package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.filter.expressions.Expression;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class AcceloFilter
{
	public static final String ALL = "_ALL";

	private Expression expression = null;

	private Search search = null;

	public AcceloFilter where(Search search) throws AcceloException
	{
		if (expression != null)
			throw new AcceloException("You may not combine filters and searches");

		this.search = search;
		return this;

	}

	public AcceloFilter where(Expression expression) throws AcceloException
	{
		if (search != null)
			throw new AcceloException("You may not combine filters and searches");

		this.expression = expression;
		return this;
	}
	
	public AcceloFilter and(Expression child) //throws AcceloException
	{
		this.expression =  new And(this.expression, child);
		
		return this;
	}
	
	
	public AcceloFilter or(Expression child) //throws AcceloException
	{
		this.expression =  new Or(this.expression, child);
		
		return this;
	}
	

	


	/*
	 * new Filter().and(new Eq(), new After()).or(new And(new Equ(), new After)
	 * new Filter().where(new Eq().and(new After()).or(new And(new Equ(), new
	 * After)
	 */

	/**
	 * Takes a map of filter key/value pairs and builds a json filter
	 * 
	 * "_filters": { "email": ["pepper@test.com", "salt@test.com"] }
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