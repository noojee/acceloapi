package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.filter.expressions.Expression;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class AcceloFilter
{
	public static final String ALL = "_ALL";

	private Expression expression = null;

	private Search search = null;

	public void where(Search search) throws AcceloException
	{
		if (expression != null)
			throw new AcceloException("You may not combine filters and searches");

		this.search = search;

	}

	public void where(Expression expression) throws AcceloException
	{
		if (search != null)
			throw new AcceloException("You may not combine filters and searches");

		this.expression = expression;
	}
	
	public AcceloFilter and(Expression child) //throws AcceloException
	{
		this.expression =  new And(this.expression, child);
		
		return this;
	}
	
	
	public AcceloFilter or(Expression child) 
	{
		this.expression =  new Or(this.expression, child);
		
		return this;
	}
	
	/**
	 * Returns the accelo json expression for this filter.
	 * 
	 * "_filters": { "_OR" : { "email": "pepper@test.com", "email" : "salt@test.com" } }
	 * 
	 * @param filterMap
	 * @return
	 */
	public String toJson()
	{
		String json = "";

		if (expression != null)
		{
			json += "\"_filters\": {\n";

			json += expression.toJson();

			json += "}";
		}
		else

		if (search != null)
		{
			json += "\"_search\": ";
			json += "\"" + search.getOperand() + "\"";
		}

		return json;
	}

	@Override
	public String toString()
	{
		return toJson();
	}

}