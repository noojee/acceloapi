package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;

class Empty<E extends AcceloEntity<E>> extends Expression
{
	private String fieldName;

	public Empty(FilterField<E, LocalDate> field)
	{
		this.fieldName = field.getFieldName();
	}

	@Override
	public String toJson()
	{
		String json = "\"empty\": [";
		json += "\"" + fieldName + "\"";

		json += "]";

		return json;
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
		Empty<E> other = (Empty<E>) obj;
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