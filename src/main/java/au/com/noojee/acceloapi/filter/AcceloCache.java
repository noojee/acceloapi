package au.com.noojee.acceloapi.filter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.expressions.Eq;

/**
 * Manages the caching of queries when accessing the Accelo REST API. By default we hold 10,000 queries with a 10 minute expiry. When a query is run we also inject each of the individual entities that
 * are returned into the cache so an access attempt by the entities id will return from cache. The cache is a singleton use AcceloCache.getInstance() to access it.
 * 
 * @author bsutton
 */
@SuppressWarnings("rawtypes")
public class AcceloCache
{

	private static Logger logger = LogManager.getLogger();

	// We cache queries and the set of entities that are returned.
	// We also create extra entries for each id so that
	// any subsequent queries by the entities id will find that entity.
	static private LoadingCache<CacheKey, List> queryCache;

	/*
	 * counts the no. of times we get a cache misses since the last resetMissCounter call.
	 */
	private int missCounter = 0;

	static private AcceloCache self;

	synchronized static public AcceloCache getInstance()
	{
		if (self == null)
			self = new AcceloCache();

		return self;
	}

	private AcceloCache()
	{
		LoadingCache<CacheKey, List> tmp = CacheBuilder.newBuilder()
				.maximumSize(10000)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.removalListener(notification ->
					{
						logger.error("Cache eviction of " + notification.getKey() + " because: " + notification.getCause());
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

	protected List<AcceloEntity> runAccelQuery(CacheKey key) throws AcceloException
	{

		long startTime = System.nanoTime();

		@SuppressWarnings("unchecked")
		List<AcceloEntity> list = AcceloApi.getInstance().getAll(key.getEndPoint(), key.getFilter(), key.getFields(), key.getResponseListClass());

		long elapsedTime = System.nanoTime() - startTime;

		logger.error("Cache miss for " + key.toString() + " Total Cache misses: " + this.missCounter + " elapsed time (ms):" + elapsedTime / 1000000);
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
				filter.where(new Eq(entity.getIdFilterField(), entity.getId()));

				idKey = new CacheKey(originalKey.getEndPoint(), filter, originalKey.getFields(), originalKey.getResponseListClass());

				put(idKey, Arrays.asList(entity));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<? extends AcceloEntity> get(CacheKey cacheKey) throws AcceloException
	{
		List<AcceloEntity> list;
		try
		{
			List<AcceloEntity> cachedList = queryCache.getIfPresent(cacheKey);
			if (cachedList == null || cacheKey.getFilter().isRefreshCache())
			{
				if (cachedList != null)
					queryCache.invalidate(cacheKey);

				// Now go out and fetch the new list.
				list = queryCache.get(cacheKey);

				// delete any ids that no longer exist in the list returned by the query.
				if (cachedList != null)
				{

					List<AcceloEntity> badEntities = cachedList.stream().filter(entity -> !list.contains(entity)).collect(Collectors.toList());

					// evict any of the badEntities
					badEntities.stream().forEach(entity ->
						{
							queryCache.invalidate(entity);
							logger.debug("Evicting: " + entity);
						});
				}
			}
			else
				list = cachedList;
		}
		catch (ExecutionException e)
		{
			throw (AcceloException) e.getCause();
		}

		return list;
	}

	void put(CacheKey key, List<AcceloEntity> list)
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
	 * Note this won't help if the entity was loaded via a query.
	 */
	public void flushEntities(List<? extends AcceloEntity> entities)
	{
		entities.stream().forEach(e -> queryCache.invalidate(e.getId()));
	}

	/**
	 * Note this won't help if the entity was loaded via a query.
	 * 
	 * @param entity
	 */
	public void flushEntity(AcceloEntity entity)
	{
		queryCache.invalidate(entity.getId());

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

}
