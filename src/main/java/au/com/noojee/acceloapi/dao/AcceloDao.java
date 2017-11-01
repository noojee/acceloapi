package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.FilterField;
import au.com.noojee.acceloapi.filter.AcceloCache;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.CacheKey;

public abstract class AcceloDao<E extends AcceloEntity<E>>
{
	static Logger logger = LogManager.getLogger();

	protected abstract Class<? extends AcceloList<E>> getResponseListClass();

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
	public List<E> getByFilter(AcceloFilter<E> filter) throws AcceloException
	{

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");

		return this.getByFilter(filter, fields);
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
	@SuppressWarnings("unchecked")
	public List<E> getByFilter(AcceloFilter<E> filter, AcceloFieldList fields) throws AcceloException
	{
		List<E> entities = new ArrayList<>();

		CacheKey<E> key = new CacheKey<>(getEndPoint(), filter, fields, getResponseListClass());
		entities = (List<E>) AcceloCache.getInstance().get(key);

		return entities;
	}

	public E getById(int id) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);

		return getById(getEndPoint(), id, fields);
	}

	protected E getById(EndPoint endpoint, int id, AcceloFieldList fields) throws AcceloException
	{
		E entity = null;
		if (id != 0)
		{
			AcceloFilter<E> filter = new AcceloFilter<>();
			FilterField<E, Integer> idField = new FilterField<E, Integer>("id");
			filter.where(filter.eq(idField, id));

			@SuppressWarnings("unchecked")
			List<E> entities = (List<E>) AcceloCache.getInstance()
					.get(new CacheKey<E>(endpoint, filter, fields, getResponseListClass()));
			if (entities.size() > 0)
				entity = entities.get(0);

		}
		return entity;
	}
	
	/**
	 * This will return 'ALL" entities associated with the end point.
	 * - USE WITH CARE!!!
	 * @param endpoint
	 * @param id
	 * @param fields
	 * @return
	 * @throws AcceloException
	 */
	@SuppressWarnings("unchecked")
	public List<E> getAll() throws AcceloException
	{
		List<E> entities = new ArrayList<>();
		
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);

		// pass in an empty filter
		AcceloFilter<E> filter = new AcceloFilter<>();


		CacheKey<E> key = new CacheKey<>(getEndPoint(), filter, fields, getResponseListClass());
		
		entities = (List<E>) AcceloCache.getInstance().get(key);

		return entities;
	}



	/**
	 * Uses introspection to collect the set of fields that need to be sent to
	 * Accelo when updating an entity.
	 * 
	 * @param ticket
	 * @throws AcceloException
	 */
	public void update(AcceloEntity<E> entity) throws AcceloException
	{

		try
		{
			AcceloFieldValues fields = new AcceloFieldValues();

			Field[] allFields = entity.getClass().getDeclaredFields();
			for (Field field : allFields)
			{
				int modifiers = field.getModifiers();
				if (Modifier.isPrivate(modifiers) && !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers))
				{
					field.setAccessible(true);
					Object value = field.get(entity);
					
					fields.add(field.getName(), (value == null ? "" : value.toString()));
				}
			}

			// Assign the Ticket to the owning aCompany.

			TicketDao.Response response = AcceloApi.getInstance().update(this.getEndPoint(), entity.getId(), fields,
					TicketDao.Response.class);
			
			logger.error(response);
			if (response == null || response.getEntity() == null)
			{
				throw new AcceloException("Failed to update " + entity.getClass().getSimpleName() + ":" + entity.getId()
						+ " details:" + this.toString());
			}
		}
		catch (IllegalAccessException | IOException e)
		{
			throw new AcceloException(e);
		}

	}
	
	
	public void delete(AcceloEntity<E> entity)
	{
		AcceloApi.getInstance().delete(getEndPoint(), entity.getId());
	}


}
