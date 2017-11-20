package au.com.noojee.acceloapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
//	public String formatAsJson()
//	{
//		Map<String, Object> fieldsAndValues = new HashMap<>();
//		
//		fieldsAndValues.putAll(fieldValues);
//		
//		fieldsAndValues.put("_fields", responseFields.fields());
//		
//		String json = new Gson().toJson(fieldsAndValues);
//
//		
//		return json;
//	}

}