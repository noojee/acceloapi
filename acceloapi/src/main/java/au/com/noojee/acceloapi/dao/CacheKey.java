package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.filter.AcceloFilter;

class CacheKey<L>
{
	EndPoint endPoint;
	AcceloFilter filter;
	private AcceloApi acceloApi;
	private AcceloFieldList fields;
	private Class<L> responseListClass;

	
	public CacheKey(AcceloApi acceloApi, EndPoint endPoint, AcceloFilter filter, AcceloFieldList fields,
			Class<L> responseListClass)
	{
		this.acceloApi = acceloApi;
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

		public AcceloApi getAcceloApi()
	{
		return acceloApi;
	}

	public AcceloFieldList getFields()
	{
		return fields;
	}


	public Class<L> getResponseListClass()
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
		@SuppressWarnings({ "unchecked", "rawtypes" })
		CacheKey<L> other = (CacheKey) obj;
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
		return "CacheKey [endPoint=" + endPoint + ", filter=" + filter + ", hashCode=" + hashCode() + "]";
	}

}