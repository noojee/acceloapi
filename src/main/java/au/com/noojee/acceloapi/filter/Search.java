package au.com.noojee.acceloapi.filter;

class Search extends Expression
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operand == null) ? 0 : operand.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Search other = (Search) obj;
		if (operand == null)
		{
			if (other.operand != null)
				return false;
		}
		else if (!operand.equals(other.operand))
			return false;
		return true;
	}



	

	
}