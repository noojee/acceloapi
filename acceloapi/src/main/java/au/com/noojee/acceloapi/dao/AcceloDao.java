package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

public abstract class AcceloDao<E extends AcceloEntity<E>, L extends AcceloList<E>>
{
	// Maintains a cache of entities by their ID.
	private HashMap<Integer, E> entityCache = new HashMap<>();

	protected abstract Class<L> getResponseListClass();

	// public abstract E getById(AcceloApi acceloApi, int id) throws
	// AcceloException;
	// pubc abstract List<E> getByFilter(AcceloApi acceloApi, AcceloFilter
	// filter) throws AcceloException;

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
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

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
			entity = entityCache.get(id);

			if (entity == null)
			{
				AcceloFilter filters = new AcceloFilter();
				filters.add(new Eq("id", id));

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
					entity = response.getList().size() > 0 ? response.getList().get(0) : null;
				entityCache.put(id, entity);
			}
		}
		return entity;
	}

}
