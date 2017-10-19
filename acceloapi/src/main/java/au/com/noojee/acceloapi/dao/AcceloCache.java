package au.com.noojee.acceloapi.dao;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

class CacheValue
{

}

public class AcceloCache<E extends AcceloEntity<E>, L extends AcceloList<E>>
{
	
	private static Logger logger = LogManager.getLogger();

	// We cache queries and the set of entities that are returned.
	// We also create extra entries for each id so that
	// any subsequent queries by the entities id will find that entity.
	static private  LoadingCache<CacheKey, List> queryCache;

	static private AcceloCache<?, ?> self;

	synchronized static AcceloCache<?, ?> getInstance()
	{
		if (self == null)
			self = new AcceloCache<>();

		return self;
	}

	AcceloCache()
	{
		LoadingCache<CacheKey, List> tmp = CacheBuilder.newBuilder().maximumSize(1000)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				// .removalListener(this)
				.build(new CacheLoader<CacheKey, List>()
				{
					@Override
					public List<E> load(CacheKey key) throws AcceloException
					{
						return AcceloCache.this.runAccelQuery(key);
					}

				});
		
		queryCache = tmp;
	}

	protected List<E> runAccelQuery(CacheKey<L> key) throws AcceloException
	{
		logger.error("Cache Missing for " + key.toString());
		List<E> list = key.getAcceloApi().getAll(key.getEndPoint(), key.getFilter(), key.getFields(),
				key.getResponseListClass());

		// We now insert the list of ids back into the cache to maximize hits
		// when getById is called.
		populateIds(key, list);

		return list;
	}

	/**
	 * Push each of the entities back into the cache so that a getById call
	 * will result in a cache hit.
	 * 
	 * @param originalKey
	 * @param list
	 * @throws AcceloException
	 */
	private void populateIds(CacheKey<L> originalKey, List<E> list) throws AcceloException
	{
		CacheKey<L> idKey;

		// Check if the filter was already for an id, in which case we do
		// nothing.
		if (!originalKey.getFilter().isIDFilter())
		{

			for (E entity : list)
			{
				AcceloFilter filter = new AcceloFilter();
				filter.where(new Eq("id", entity.getId()));

				idKey = new CacheKey<>(originalKey.getAcceloApi(), originalKey.getEndPoint(), filter,
						originalKey.getFields(), originalKey.getResponseListClass());
				
				put(idKey, Arrays.asList(entity));
			}
		}
	}

	List<E> get(CacheKey<L> cacheKey) throws AcceloException 
	{
		List<E> list;
		try
		{
			if (cacheKey.getFilter().isInvalideCache())
				queryCache.invalidate(cacheKey);
			list = queryCache.get(cacheKey);
		}
		catch (ExecutionException e)
		{
			throw (AcceloException)e.getCause();
		}
		
		return list;
	}

	void put(CacheKey<L> key, List<E> list)
	{
		queryCache.put(key, list);
	}

	/*
	 * Adds new entries to the cache. If it finds an entry in the cache then it
	 * returns that entry as the entry is likely to have other entities attached
	 * to it.
	 * 
	 */
	// @SuppressWarnings("unchecked")
	// public List<CacheValue> updateCache(List<CacheValue> values)
	// {
	// List<CacheValue> updated = new ArrayList<>();
	//
	//
	// // Add them to the cache
	// for (E entity : entities)
	// {
	// CacheKey key = new CacheKey(getEndPoint(), entity.getId());
	//
	// AcceloEntity<E> existingEntity = getCache().get(key);
	// if (existingEntity == null)
	// {
	// getCache().put(key, entity);
	// existingEntity = entity;
	// }
	// updated.add((E) existingEntity);
	// }
	// return updated;
	// }

}
