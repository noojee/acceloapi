package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

public class AcceloFieldList
{
	public static String _ALL = "_ALL";
	public static final AcceloFieldList ALL = new AcceloFieldList(_ALL);

	private List<String> fields = new ArrayList<>();

	public AcceloFieldList(String field)
	{
		fields.add(field);
	}

	public AcceloFieldList()
	{
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
		return formatAsJson() + " fields:" + hashCode();
	}
	/**
	 * takes a list of field names and formats them into a json list.
	 * 
	 * e.g. "_fields": ["status.title", "status.id", "status.color", "mobile"]
	 * 
	 * @param fieldNames
	 * @return
	 */

	public String formatAsJson()
	{

		String json = "";
		boolean firstField = true;

		for (String field : this.fields())
		{
			if (firstField)
			{
				json += "\"_fields\": [";
				firstField = false;
			}
			else
				json += ",";

			json += "\"" + field + "\"";

		}
		if (!firstField)
			json += "]";
		return json;

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
