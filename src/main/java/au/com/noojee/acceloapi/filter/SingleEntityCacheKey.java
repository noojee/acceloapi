package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.dao.AcceloResponseMeta;
import au.com.noojee.acceloapi.entities.AcceloEntity;

/**
 * This class is used to differentiate between a cache entry for a single id verses a full query.
 * @author bsutton
 *
 * @param <E>
 */
public class SingleEntityCacheKey<E extends AcceloEntity<E>> extends CacheKey<E>
{

	private int id;

	public SingleEntityCacheKey(EndPoint endPoint, AcceloFilter<E> filter, AcceloFieldList fields,
			Class<? extends AcceloResponseMeta<E>> responseListClass, Class<E> entityClass, int id)
	{
		super(endPoint, filter, fields, responseListClass, entityClass);
		
		this.id = id;
	}
	
	int getId()
	{
		return this.id;
	}
	
	
}
