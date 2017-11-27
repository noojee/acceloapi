package au.com.noojee.acceloapi.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;

import au.com.noojee.acceloapi.AcceloAbstractResponseList;
import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.dao.gson.GsonForAccelo;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.filter.AcceloFilter;

/**
 * Manages the caching of queries when accessing the Accelo REST API. By default we hold 10,000 queries with a 10 minute
 * expiry. When a query is run we also inject each of the individual entities that are returned into the cache so an
 * access attempt by the entities id will return from cache. The cache is a singleton use AcceloCache.getInstance() to
 * access it.
 * 
 * @author bsutton
 */
@SuppressWarnings("rawtypes")
public enum AcceloCache
{
	SELF;

	private static Logger logger = LogManager.getLogger();

	// We cache queries and the set of entities that are returned.
	// We also create extra entries for each id so that
	// any subsequent queries by the entities id will find that entity.
	private LoadingCache<CacheKey, List> queryCache;

	/*
	 * counts the no. of times we get a cache misses since the last resetMissCounter call.
	 */
	private int missCounter = 0;

	synchronized static public AcceloCache getInstance()
	{
		return SELF;
	}

	private AcceloCache()
	{
		LoadingCache<CacheKey, List> tmp = CacheBuilder.newBuilder()
				.maximumSize(100000)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.removalListener(notification ->
					{
						// if (!(notification.getKey() instanceof SingleEntityCacheKey))
						if (notification.getCause() 
								!= RemovalCause.REPLACED)
							logger.error(
									"Cache eviction of " + notification.getKey() + " because: "
											+ notification.getCause());
					})
				.build(new CacheLoader<CacheKey, List>()
				{
					@Override
					public List<AcceloEntity> load(CacheKey key) throws AcceloException
					{
						AcceloCache.this.missCounter++;
						return AcceloCache.this.runAccelQuery(key);
					}

				});

		queryCache = tmp;
	}

	@SuppressWarnings("unchecked")

	private List<AcceloEntity> runAccelQuery(CacheKey key) throws AcceloException
	{
		List<AcceloEntity> list;

		if ( AcceloAbstractResponseList.class.isAssignableFrom(key.getMetaResponseClass()))
		{

			list = AcceloApi.getInstance().getAll(key.getEndPoint(), key.getFilter(), key.getFields(),
					key.getMetaResponseClass());
		}
		else
		{
			AcceloEntity entity = AcceloApi.getInstance().get(key.getEndPoint(), key.getFilter(), key.getFields(),
					key.getMetaResponseClass());

			list = new ArrayList<>();
			if (entity != null)
				list.add(entity);
		}

		// We now insert the list of ids back into the cache to maximize hits
		// when getById is called.
		populateIds(key, list);

		return list;
	}

	/**
	 * Push each of the entities back into the cache so that a getById call will result in a cache hit.
	 * 
	 * @param originalKey
	 * @param list
	 * @throws AcceloException
	 */
	@SuppressWarnings("unchecked")
	private void populateIds(CacheKey originalKey, List<AcceloEntity> list) throws AcceloException
	{
		CacheKey idKey;

		// Check if the filter was already for an id, in which case we do
		// nothing.
		if (!originalKey.getFilter().isIDFilter())
		{

			for (AcceloEntity entity : list)
			{
				AcceloFilter filter = new AcceloFilter();
				filter.where(filter.eq(entity.getIdFilterField(), entity.getId()));

				idKey = new SingleEntityCacheKey(originalKey.getEndPoint(), filter, originalKey.getFields(),
						originalKey.getMetaResponseClass(), entity.getClass(), entity.getId());

				put(idKey, Arrays.asList(entity));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<? extends AcceloEntity> get(CacheKey cacheKey) throws AcceloException
	{
		boolean returnedFromCache = false;
		List<AcceloEntity> list;

		long startTime = System.nanoTime();

		try
		{

			List<AcceloEntity> cachedList = queryCache.getIfPresent(cacheKey);
			if (cachedList == null || cacheKey.getFilter().isRefreshCache())
			{
				if (cachedList != null)
					queryCache.invalidate(cacheKey);

				// We need to clone the key as it must be invariant
				// We have had instances where the filter contained in the
				// key is modified after being passed to us which breaks the key.
				CacheKey immutableKey = cacheKey.copy();

				// Now go out and fetch the new list.
				list = queryCache.get(immutableKey);

				// delete any ids that no longer exist in the list returned by the query.
				if (cachedList != null)
				{

					List<AcceloEntity> badEntities = cachedList.stream().filter(entity -> !list.contains(entity))
							.collect(Collectors.toList());

					// evict any of the badEntities
					badEntities.stream().forEach(entity ->
						{
							queryCache.invalidate(entity);
							logger.debug("Evicting: " + entity);
						});
				}
			}
			else
			{
				returnedFromCache = true;
				list = cachedList;
			}
		}
		catch (ExecutionException e)
		{
			throw (AcceloException) e.getCause();
		}

		// We always return a cloned list as we don't want anyone changing the data in the cache accidentally.
		List<AcceloEntity> listCopy = list.stream().map(e -> copy(e)).collect(Collectors.toList());

		long elapsedTime = System.nanoTime() - startTime;

		logger.error("Returned from cache: " + returnedFromCache + " " + cacheKey + " entities returned: " + list.size()
				+ " Misses: "
				+ this.missCounter
				+ " timer: " + elapsedTime / 1000000 + "(ms)");

		return listCopy;
	}

	@SuppressWarnings("unchecked")
	private AcceloEntity copy(AcceloEntity rhs)
	{
		String json = GsonForAccelo.toJson(rhs);

		AcceloEntity copy = GsonForAccelo.fromJson(json, rhs.getClass());

		return copy;
	}

	private void put(CacheKey key, List<AcceloEntity> list)
	{
		queryCache.put(key, list);
	}

	public void resetMissCounter()
	{
		this.missCounter = 0;
	}

	/**
	 * Returns the no. of cache misses since the last call to resetMissCounter;
	 * 
	 * @return
	 */
	public int getMissCounter()
	{
		return this.missCounter;
	}

	/**
	 * Flush every entity/query from the cache.
	 */
	public void flushCache()
	{
		queryCache.invalidateAll();
		this.missCounter = 0;
	}

	/*
	 * Flush the list of entities from the cache.
	 */
	public void flushEntities(List<? extends AcceloEntity> entities)
	{
		entities.stream().forEach(e -> flushEntity(e));
	}

	/**
	 * Flush the given key from the cache.
	 * 
	 * @param key
	 */
	public void flushQuery(CacheKey key)
	{
		queryCache.invalidate(key);
	}

	public void flushEntity(AcceloEntity entity)
	{
		flushEntity(entity, false);
	}

	/*
	 * Flushes the given entity from the cache. search the cache for any CacheKeys that are the same type as entity.
	 * This should find both single entities inserted by populateIds as well as entities contained as part of a queries
	 * list. If flushQueries is true then we will also flush any queries that contain the entity.
	 */
	@SuppressWarnings("unchecked")
	public void flushEntity(AcceloEntity entity, boolean flushQueries)
	{
		queryCache.asMap().keySet().stream().filter(k -> k.getEntityClass() == entity.getClass())
				.forEach(k ->
					{
						// For a single entity added by populateid then we want to remove it.
						if (k instanceof SingleEntityCacheKey)
						{
							SingleEntityCacheKey seck = (SingleEntityCacheKey) k;
							if (seck.getId() == entity.getId())
								queryCache.invalidate(k);
						}
						else
						{
							// For a query we want to prune out the deleted entity.
							// Get the list from the cache for the key.
							// this could be a list of 0, 1 or many
							List<? extends AcceloEntity> list;
							try
							{
								if (flushQueries)
									queryCache.invalidate(k); // we have been instructed to take out the whole query.
								else
								{
									list = queryCache.get(k);
									list.remove(entity);
									// Even if the list is now empty we don't invalidate the cache key as we support
									// negative caching. i.e. if a query returns zero results don't run it again.
								}
							}
							catch (ExecutionException e)
							{
								logger.error(e, e);
							}
						}

					});

	}

	/**
	 * Finds the entity (by its id and type) any where in the cache and updates it.
	 * 
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	public void updateEntity(AcceloEntity entity)
	{
		queryCache.asMap().keySet().stream().filter(k -> k.getEntityClass() == entity.getClass())
				.forEach(k ->
					{
						// For a single entity added by populateid then we want to remove it.
						if (k instanceof SingleEntityCacheKey)
						{
							SingleEntityCacheKey seck = (SingleEntityCacheKey) k;
							if (seck.getId() == entity.getId())
							{
								// replace the entity with the new one.
								queryCache.put(k, Arrays.asList(entity));
							}
						}
						else
						{
							// For a query we want to prune out the deleted entity.
							// Get the list from the cache for the key.
							// this could be a list of 0, 1 or many
							List<AcceloEntity> list;
							try
							{
								list = queryCache.get(k);

								// find and remove the old version of the entity.
								list.stream().filter(e -> e.getId() == entity.getId())
										.findFirst()
										.ifPresent(element -> list.remove(element));

								// add the new version of the entity.
								list.add(entity);
							}
							catch (ExecutionException e)
							{
								logger.error(e, e);
							}
						}

					});

	}

	@SuppressWarnings("unchecked")
	public List<AcceloEntity> getIfPresent(CacheKey<Ticket> key)
	{
		return this.queryCache.getIfPresent(key);
	}

}
