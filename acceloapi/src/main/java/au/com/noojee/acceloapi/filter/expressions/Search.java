package au.com.noojee.acceloapi.filter.expressions;

public class Search 
{
	private String operand;

	public Search(String operand)
	{
		this.operand = operand;
	}

	public String toJson()
	{
		String json = "";

		json += "\"_search\": ";

		json += "\"" + operand + "\"";

		return json;
	}

	public String getOperand()
	{
		return operand;
	}
	
}