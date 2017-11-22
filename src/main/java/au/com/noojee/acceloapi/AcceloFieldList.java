package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AcceloFieldList
{
	public static final String _ALL = "_ALL";
	public static final AcceloFieldList ALL = new AcceloFieldList(_ALL);

	@SerializedName("_fields")
	private List<String> fields = new ArrayList<>();

	public AcceloFieldList(String field)
	{
		fields.add(field);
	}

	public AcceloFieldList()
	{
	}

	public AcceloFieldList copy()
	{
		AcceloFieldList fieldList = new AcceloFieldList();
		for (String field : fields)
		{
			fieldList.add(field);
		}
		return fieldList;
	}

	public void add(String fieldName)
	{
		fields.add(fieldName);
	}

	public String[] fields()
	{
		return fields.toArray(new String[1]);
	}

	@Override
	public String toString()
	{
		return String.join(", ", fields);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
		AcceloFieldList other = (AcceloFieldList) obj;
		if (fields == null)
		{
			if (other.fields != null)
				return false;
		}
		else if (!fields.equals(other.fields))
			return false;
		return true;
	}

}
