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
}