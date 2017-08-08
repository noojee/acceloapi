package au.com.noojee.acceloapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AcceloFieldValues
{
	Map<String, String> fields = new HashMap<String, String>();

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
		String jsonFieldValues = "";

		for (String name : this.keys())
		{
			if (jsonFieldValues.length() > 0)
				jsonFieldValues += ",";
			
			jsonFieldValues += "\"" + name + "\":" + "\"" + this.get(name) + "\"";
		}

		return "\"_fields:\" {" + jsonFieldValues + "}";

	}

	public String buildUrlArgs() throws UnsupportedEncodingException
	{
		String args = "";
		for (String name : this.keys())
		{
			if (args.length() > 0)
				args += "&";
			
			args += URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(this.get(name), "UTF-8");
		}
		return args;
	}

}