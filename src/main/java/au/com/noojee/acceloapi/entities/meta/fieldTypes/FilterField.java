package au.com.noojee.acceloapi.entities.meta.fieldTypes;

import au.com.noojee.acceloapi.entities.AcceloEntity;

public class FilterField<E extends AcceloEntity<E>, T>
{

	private String fieldName;

	public FilterField(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldName()
	{
		return this.fieldName;
	}

	public FilterField<E,T> copy()
	{
		return new FilterField<>(this.fieldName);
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
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
		@SuppressWarnings("unchecked")
		FilterField<E, T> other = (FilterField<E, T>) obj;
		if (fieldName == null)
		{
			if (other.fieldName != null)
				return false;
		}
		else if (!fieldName.equals(other.fieldName))
			return false;
		return true;
	}

	
}
