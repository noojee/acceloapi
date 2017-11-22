package au.com.noojee.acceloapi.cache;

import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.dao.AcceloResponseMeta;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.AcceloFilter;

@SuppressWarnings("rawtypes")
public class CacheKey<E extends AcceloEntity<E>>
{
	EndPoint endPoint;
	AcceloFilter<E> filter;
	private AcceloFieldList fields;
	
	// The following two don't form part of the key as the endPoint implies these.
	private transient Class<? extends AcceloResponseMeta<E>> responseClass;
	private transient Class<E> entityClass;

	
	public CacheKey(EndPoint endPoint, AcceloFilter<E> filter, AcceloFieldList fields,
			Class<? extends AcceloResponseMeta<E>> responseClass, Class<E> entityClass)
	{
		this.endPoint = endPoint;
		this.filter = filter;
		this.fields = fields;
		this.responseClass = responseClass;
		this.entityClass = entityClass;
	}
	
	public Class<E> getEntityClass()
	{
		return this.entityClass;
	}

	public AcceloFilter<E> getFilter()
	{
		return filter;
	}


	public EndPoint getEndPoint()
	{
		return endPoint;
	}

	public AcceloFieldList getFields()
	{
		return fields;
	}


	public Class<? extends AcceloResponseMeta<E>> getMetaResponseClass()
	{
		return responseClass;
	}
	
	public CacheKey<E> copy()
	{
		AcceloFilter<E> filter = this.filter.copy();
		AcceloFieldList fields = this.fields.copy();
		
		CacheKey<E> newKey = new CacheKey<>(this.endPoint, filter, fields, this.responseClass, this.entityClass);
		
		return newKey;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
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
		@SuppressWarnings({ })
		CacheKey other = (CacheKey) obj;
		if (endPoint != other.endPoint)
			return false;
		if (fields == null)
		{
			if (other.fields != null)
				return false;
		}
		else if (!fields.equals(other.fields))
			return false;
		if (filter == null)
		{
			if (other.filter != null)
				return false;
		}
		else if (!filter.equals(other.filter))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CacheKey [endPoint=" + endPoint + ", filter=" + filter + " hashCode:" + hashCode() + "]";
	}

}