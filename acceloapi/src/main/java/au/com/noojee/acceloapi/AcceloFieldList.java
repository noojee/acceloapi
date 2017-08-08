package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

public class AcceloFieldList
{
	public static String _ALL = "_ALL";
	public static final AcceloFieldList ALL = new AcceloFieldList(_ALL);

	

	List<String> fields = new ArrayList<String>();

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

}
