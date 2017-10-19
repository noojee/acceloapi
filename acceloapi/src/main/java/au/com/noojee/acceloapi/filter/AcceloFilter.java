package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.filter.expressions.Eq;
import au.com.noojee.acceloapi.filter.expressions.Expression;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class AcceloFilter
{
	public static final String ALL = "_ALL";

	private Expression expression = null;

	private Search search = null;

	private boolean invalidateCache = false;

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
	 * Use this method to invalid any cache data associated with this filter.
	 * This will force the system to re-fetch the data from the accelo servers.
	 */
	public void invalidateCache()
	{
		this.invalidateCache = true;
	}
	
	public boolean isInvalideCache()
	{
		return invalidateCache;
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
		return toJson().replaceAll("\n", " ") + " filter hashcode:" + hashCode();
	} 

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
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
		AcceloFilter other = (AcceloFilter) obj;
		if (expression == null)
		{
			if (other.expression != null)
				return false;
		}
		else if (!expression.equals(other.expression))
			return false;
		if (search == null)
		{
			if (other.search != null)
				return false;
		}
		else if (!search.equals(other.search))
			return false;
		return true;
	}

	/**
	 * Returns true if the filter is for a simple id match.
	 * @return
	 */
	public boolean isIDFilter()
	{
		boolean isIDFilter = false;
		
		if (expression != null && expression instanceof Eq)
		{
			Eq simple = (Eq)expression;
			if (simple.isFieldName("id"))
				isIDFilter = true;
		}
			
		return isIDFilter;
	}


}