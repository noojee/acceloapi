package au.com.noojee.acceloapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class AcceloFieldValues
{
	private Map<String, String> fieldValues = new HashMap<>();
	
	@SerializedName("_fields")
	private AcceloFieldList responseFields = null; 
	
	
	
	public AcceloFieldValues()
	{
		this.responseFields = AcceloFieldList.ALL;
	}

	public AcceloFieldValues(AcceloFieldList responseFields)
	{
		this.responseFields = responseFields;
	}

	public void add(String name, int i)
	{
		fieldValues.put(name, "" + i);
	}

	public Set<String> keys()
	{
		// TODO Auto-generated method stub
		return fieldValues.keySet();
	}

	public String get(String name)
	{
		return fieldValues.get(name);
	}

	public void add(String name, String value)
	{
		fieldValues.put(name, value);

	}

	/**
	 * takes a list of field name/value pairs and formats them to json ready to push. Example: { "subject": "Support
	 * Request via Accelo The website has become unresponsive", "nonbillable": "0", "class": "1","rate_charged": "0.00"
	 * }
	 * 
	 * @param fieldNameValues
	 * @return
	 */
//	public String formatAsJson()
//	{
//		String json = new Gson().toJson(fieldValues);
//
//		return json;
//
//		// return json.substring(1, json.length()-2);
//
//	}

	/**
	 * Builds the gson for the set of fields and embeds the list of result fields that are required.
	 * 
	 * <pre>
	 * Example: 
	 * 	{ "subject": "Support Request via Accelo The website has become
	 * 		unresponsive", "nonbillable": "0", "class": "1","rate_charged": "0.00" 
	 *   "_fields" : ["ALL"]
	 * }
	 * </pre>
	 * 
	 * @param all
	 * @return
	 */
	public String formatAsJson()
	{
		Map<String, Object> x = new HashMap<>();
		
		x.putAll(fieldValues);
		
		x.put("_fields", responseFields.fields());
		
		String json = new Gson().toJson(x);

		
		return json;
	}

}