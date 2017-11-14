package au.com.noojee.acceloapi.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import au.com.noojee.acceloapi.AcceloAbstractResponseList;
import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.HTTPResponse;
import au.com.noojee.acceloapi.Meta;
import au.com.noojee.acceloapi.cache.AcceloCache;
import au.com.noojee.acceloapi.cache.CacheKey;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.generator.FilterField;
import au.com.noojee.acceloapi.entities.generator.JsonValidator;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public abstract class AcceloDao<E extends AcceloEntity<E>>
{
	static Logger logger = LogManager.getLogger();

	protected abstract Class<? extends AcceloAbstractResponseList<E>> getResponseListClass();

	protected abstract Class<? extends AcceloResponse<E>> getResponseClass();

	protected abstract EndPoint getEndPoint();

	protected abstract Class<E> getEntityClass();

	/**
	 * Returns the list of tickets that match the pass in filter.
	 * 
	 * @param acceloApi
	 * @param filter the filter defining the tickets to be returned.
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
	 * @param filter the filter defining the tickets to be returned.
	 * @param fileds - the set of fields to return
	 * @return
	 * @throws AcceloException
	 * @throws ExecutionException
	 */
	@SuppressWarnings("unchecked")
	public List<E> getByFilter(AcceloFilter<E> filter, AcceloFieldList fields) throws AcceloException
	{
		List<E> entities = new ArrayList<>();

		CacheKey<E> key = new CacheKey<>(getEndPoint(), filter, fields, getResponseListClass(), this.getEntityClass());
		entities = (List<E>) AcceloCache.getInstance().get(key);

		return entities;
	}

	public E getById(int id) throws AcceloException
	{
		return getById(getEndPoint(), id, AcceloFieldList.ALL);
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
					.get(new CacheKey<E>(endpoint, filter, fields, getResponseListClass(), this.getEntityClass()));
			if (entities.size() > 0)
				entity = entities.get(0);

		}
		return entity;
	}

	/**
	 * This will return 'ALL" entities associated with the end point. - USE WITH CARE!!!
	 * 
	 * @param endpoint
	 * @param id
	 * @param fieldValues
	 * @return
	 * @throws AcceloException
	 */
	@SuppressWarnings("unchecked")
	public List<E> getAll() throws AcceloException
	{
		List<E> entities = new ArrayList<>();

		// pass in an empty filter
		AcceloFilter<E> filter = new AcceloFilter<>();

		CacheKey<E> key = new CacheKey<>(getEndPoint(), filter,  AcceloFieldList.ALL, getResponseListClass(), this.getEntityClass());

		entities = (List<E>) AcceloCache.getInstance().get(key);

		return entities;
	}
	
	
	/**
	 * Inserts an entity into Accelo.
	 * 
	 * Becareful when doing inserts as any cached queries will not included the newly inserted entity.
	 * You may need to identify any cached queries and flush them.
	 * 
	 * @param entity
	 * 
	 * @return the newly inserted entity as returned from Accelo.
	 */
	public E insert(AcceloEntity<E> entity)
	{
		AcceloFieldValues fields = marshalFields(entity);

		AcceloResponse<E> response = AcceloApi.getInstance().insert(this.getEndPoint(), fields,
				this.getResponseClass());
		logger.error(response);
		
		return response.getEntity();
	}

	/**
	 * Updated an existing entity.
	 * Any instances in the cache will be updated.
	 * 
	 * @param ticket
	 * @throws AcceloException
	 */
	public E update(AcceloEntity<E> entity)
	{
		AcceloFieldValues fields = marshalFields(entity);

		AcceloResponse<E> response = AcceloApi.getInstance().update(this.getEndPoint(), entity.getId(), fields,
				this.getResponseClass());

		logger.error(response);
		if (response == null || response.getEntity() == null)
		{
			throw new AcceloException("Failed to update " + entity.getClass().getSimpleName() + ":" + entity.getId()
					+ " details:" + this.toString());
		}
		
		AcceloCache.getInstance().updateEntity(response.getEntity());
		
		return response.getEntity();
	}

	/**
	 * Uses reflection to build the AcceloFieldValues. We only marshal private fields that are not transient and not
	 * static.
	 * 
	 * @param entity
	 * @return
	 * @throws IllegalAccessException
	 */
	private AcceloFieldValues marshalFields(AcceloEntity<E> entity)
	{
		AcceloFieldValues fields = new AcceloFieldValues();

		try
		{
			Field[] entityFields = entity.getClass().getDeclaredFields();
			Field[] inheritedFields = entity.getClass().getSuperclass().getDeclaredFields();
			List<Field> fieldList = new ArrayList<>(Arrays.asList(entityFields));
			fieldList.addAll(Arrays.asList(inheritedFields));
			for (Field field : fieldList)
			{
				int modifiers = field.getModifiers();
				if (Modifier.isPrivate(modifiers) && !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers))
				{
					field.setAccessible(true);
					Object value;
					value = field.get(entity);

					fields.add(field.getName(), (value == null ? "" : value.toString()));
				}
			}
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new AcceloException(e);
		}

		return fields;
	}

	public void delete(AcceloEntity<E> entity)
	{
		HTTPResponse response = AcceloApi.getInstance().delete(getEndPoint(), entity.getId());

		if (response.getResponseCode() != 200 && response.getResponseCode() != 400)
			throw new AcceloException("Delete failed with error code: " + response.getResponseCode() + " Message:"
					+ response.getResponseMessage());
		else
			// We remove the entity from the cache. Of course it may also be attached to a query.
			AcceloCache.getInstance().flushEntity(entity);

	}
	
	
	/**
	 * In same cases (such as activity) we can't update the entity so we
	 * have to insert a new (cloned) entity and then delete the old one.
	 * 
	 * To use this method, retrieve an entity from accelo.
	 * Adjust the details of the entity and then call replace.
	 * 
	 * This method will delete the original entity using its id and then
	 * insert an new replacement entity based on the entity you pass in.
	 * 
	 * This method will also flush the cache, including any queries that 
	 * contained the original entity.
	 * 
	 * @param activity
	 */
	public E replace(E entity)
	{
		// We need to clear the query from the cache now as after the
		// delete/insert the query will be invalid but the entity won't exists
		// so we can flush it.
		AcceloCache.getInstance().flushEntity(entity, true);
		
		// we do the insert first so if anything goes wrong we don't loose data.
		E replacementEntity = insert(entity);

		delete(entity);
		
		
		
		return replacementEntity;
	}



	public void validateEntityClass()
	{

		AcceloApi api = AcceloApi.getInstance();
		
		AcceloFilter<E> filter = new AcceloFilter<>();

		String rawResponse = api.getRaw(this.getEndPoint(), filter, AcceloFieldList.ALL, this.getResponseListClass());
		@SuppressWarnings("unchecked")
		ResponseForValidation response = new Gson().fromJson(rawResponse, ResponseForValidation.class);
		
		JsonValidator.validate(getEntityClass(), response.response[0]);

	}
	
	public class ResponseForValidation
	{
		Meta meta;
		Entity[] response;
		
	}

	static class Entity extends HashMap<String, String>
	{
		private static final long serialVersionUID = 1L;
	}

}
