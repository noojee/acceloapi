package au.com.noojee.acceloapi.filter.expressions;

public class Search 
{
	private String operand;

	/**
	 * Sets up a full text search over the fields defined for this entity. 
	 * @param seachValue
	 */
	public Search(String seachValue)
	{
		this.operand = seachValue;
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