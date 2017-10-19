package au.com.noojee.acceloapi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	@SuppressWarnings("rawtypes")
	static private AcceloCache cache = new AcceloCache(); 
	
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
	 * @throws ExecutionException 
	 */
	public List<E> getByFilter(AcceloApi acceloApi, AcceloFilter filter, AcceloFieldList fields) throws AcceloException
	{
		List<E> entities = new ArrayList<>();

		CacheKey<L> key = new CacheKey<>(acceloApi, getEndPoint(), filter, fields, getResponseListClass());
		entities = cache.get(key);

		return entities;
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
			AcceloFilter filter = new AcceloFilter();
			filter.where(new Eq("id", id));

			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<E> entities = AcceloCache.getInstance().get(new CacheKey(acceloApi, endpoint, filter, fields, getResponseListClass()));
			if (entities.size() > 0)
				entity = entities.get(0);

		}
		return entity;
	}

}
