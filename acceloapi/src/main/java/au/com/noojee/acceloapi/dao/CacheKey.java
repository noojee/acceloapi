package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.AcceloFilter;

@SuppressWarnings("rawtypes")
class CacheKey<E extends AcceloEntity>
{
	EndPoint endPoint;
	AcceloFilter filter;
	private AcceloFieldList fields;
	private Class<? extends AcceloList<E>> responseListClass;

	
	public CacheKey(EndPoint endPoint, AcceloFilter filter, AcceloFieldList fields,
			Class<? extends AcceloList<E>> responseListClass)
	{
		this.endPoint = endPoint;
		this.filter = filter;
		this.fields = fields;
		this.responseListClass = responseListClass;

	}

	public AcceloFilter getFilter()
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


	public Class<? extends AcceloList<E>> getResponseListClass()
	{
		return responseListClass;
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
		return "CacheKey [endPoint=" + endPoint + ", filter=" + filter + "]";
	}

}