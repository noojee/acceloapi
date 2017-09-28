package au.com.noojee.acceloapi.filter.expressions;

public class Empty implements Expression
{
	private String fieldName;

	public Empty(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String toJson()
	{
		String json = "\"empty\": [";
		json += "\"" + fieldName + "\"";

		json += "]";

		return json;
	}
}