package au.com.noojee.acceloapi.filter;

import java.util.ArrayList;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.filter.expressions.Compound;
import au.com.noojee.acceloapi.filter.expressions.Expression;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class AcceloFilter
{
	public static final String ALL = "_ALL";

	ArrayList<Expression> expressions = new ArrayList<>();

	private Search search = null;

	public AcceloFilter add(Search search) throws AcceloException
	{
		if (expressions.size() > 0)
			throw new AcceloException("You may not combine filters and searches");

		this.search = search;
		return this;

	}

	public Compound add(Compound expression) throws AcceloException
	{
		if (search != null)
			throw new AcceloException("You may not combine filters and searches");

		expressions.add(expression);
		return expression;
	}

	public AcceloFilter add(Expression expression) throws AcceloException
	{
		if (search != null)
			throw new AcceloException("You may not combine filters and searches");

		expressions.add(expression);
		return this;
	}

	
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
		boolean firstFilter = true;

		for (Expression expression : expressions)
		{
			if (firstFilter)
			{
				json += "\"_filters\": {\n";
				firstFilter = false;
			}
			else
				json += ",";

			json += expression.toJson();

		}
		if (!firstFilter)
			json += "}";

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