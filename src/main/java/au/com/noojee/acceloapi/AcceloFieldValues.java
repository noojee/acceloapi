package au.com.noojee.acceloapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class AcceloFieldValues
{
	Map<String, String> fields = new HashMap<>();

	public void add(String name, int i)
	{
		fields.put(name, "" + i);
	}

	public Set<String> keys()
	{
		// TODO Auto-generated method stub
		return fields.keySet();
	}

	public String get(String name)
	{
		return fields.get(name);
	}

	public void add(String name, String value)
	{
		fields.put(name, value);

	}

	/**
	 * takes a list of field name/value pairs and formats them to json ready to
	 * push.
	 * 
	 * Example: { "subject": "Support Request via Accelo The website has become
	 * unresponsive", "nonbillable": "0", "class": "1","rate_charged": "0.00" }
	 * 
	 * @param fieldNameValues
	 * @return
	 */
	public String formatAsJson()
	{
		String json = new Gson().toJson(fields);
		
		return json;
		
		//return json.substring(1, json.length()-2);
		
		
	}

}