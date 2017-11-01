package au.com.noojee.acceloapi.filter.expressions;

public class Empty extends Expression
{
	private String fieldName;

	public Empty(String fieldName)
	{
		this.fieldName = fieldName;
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
		Empty other = (Empty) obj;
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