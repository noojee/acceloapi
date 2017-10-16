package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

public abstract class AcceloDao<E extends AcceloEntity<E>, L extends AcceloList<E>>
{
	static Logger logger = LogManager.getLogger();

	private static final String CACHE_NAME = "entityCache";
	// Maintains a cache of entities.
	static private final CacheManager cacheManager = CacheManagerBuilder
			.newCacheManagerBuilder().withCache(CACHE_NAME, CacheConfigurationBuilder
					.newCacheConfigurationBuilder(CacheKey.class, AcceloEntity.class, ResourcePoolsBuilder.heap(5000)))
			.build(true);

	protected abstract Class<L> getResponseListClass();

	protected abstract EndPoint getEndPoint();

	/**
	 * Returns the list of tickets that match the pass in filter.
	 * 
	 * @param acceloApi
	 * @param filter
	 *            the filter defining the tickets to be returned.
	 * @return
	 * @throws AcceloException
	 */
	public List<E> getByFilter(AcceloApi acceloApi, AcceloFilter filter) throws AcceloException
	{

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");

		return this.getByFilter(acceloApi, filter, fields);
	}

	/**
	 * Returns the list of tickets that match the pass in filter.
	 * 
	 * @param acceloApi
	 * @param filter
	 *            the filter defining the tickets to be returned.
	 * @param fileds
	 *            - the set of fields to return
	 * @return
	 * @throws AcceloException
	 */
	public List<E> getByFilter(AcceloApi acceloApi, AcceloFilter filter, AcceloFieldList fields) throws AcceloException
	{
		List<E> entities = new ArrayList<>();

		try
		{
			entities = acceloApi.getAll(getEndPoint(), filter, fields, getResponseListClass());
			entities = updateCache(entities);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return entities;
	}

	/*
	 * Adds new entries to the cache. If it finds an entry in the cache then it
	 * returns that entry as the entry is likely to have other entities attached
	 * to it.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private List<E> updateCache(List<E> entities)
	{
		List<E> updated = new ArrayList<>();

		@SuppressWarnings("rawtypes")
		Cache<CacheKey, AcceloEntity> cache = cacheManager.getCache(CACHE_NAME, CacheKey.class, AcceloEntity.class);
		// Add them to the cache
		for (E entity : entities)
		{
			CacheKey key = new CacheKey(getEndPoint(), entity.getId());

			AcceloEntity<E> existingEntity = cache.get(key);
			if (existingEntity == null)
			{
				cache.put(key, entity);
				existingEntity = entity;
			}
			updated.add((E) existingEntity);
		}
		return updated;
	}

	public E getById(AcceloApi acceloApi, int id) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);

		return getById(acceloApi, getEndPoint(), id, fields);
	}

	protected E getById(AcceloApi acceloApi, EndPoint endpoint, int id, AcceloFieldList fields) throws AcceloException
	{
		E entity = null;
		if (id != 0)
		{
			entity = (E) cacheManager.getCache(CACHE_NAME, CacheKey.class, AcceloEntity.class)
					.get(new CacheKey(endpoint, id));

			if (entity == null)
			{
				AcceloFilter filters = new AcceloFilter();
				filters.where(new Eq("id", id));

				L response;
				try
				{
					response = acceloApi.get(endpoint, filters, fields, getResponseListClass());
				}
				catch (IOException e)
				{
					throw new AcceloException(e);
				}

				if (response != null)
				{
					entity = response.getList().size() > 0 ? response.getList().get(0) : null;
					if (entity != null)
						cacheManager.getCache(CACHE_NAME, CacheKey.class, AcceloEntity.class)
								.put(new CacheKey(endpoint, id), entity);
					else
						logger.error("Failed to find entity for key: " + new CacheKey(endpoint, id));
				}
			}
		}
		return entity;
	}

	static class CacheKey
	{
		EndPoint endPoint;
		int id;

		public CacheKey(EndPoint endPoint, int id)
		{
			this.endPoint = endPoint;
			this.id = id;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (endPoint != other.endPoint)
				return false;
			if (id != other.id)
				return false;
			return true;
		}
	}

}
