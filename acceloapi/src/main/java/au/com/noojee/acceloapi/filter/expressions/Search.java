package au.com.noojee.acceloapi.filter.expressions;

public class Search implements Expression
{
	private String operand;

	public Search(String operand)
	{
		this.operand = operand;
	}

	@Override
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