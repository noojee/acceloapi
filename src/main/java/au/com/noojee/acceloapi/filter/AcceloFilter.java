package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.generator.FilterField;
import au.com.noojee.acceloapi.entities.meta.AgainstType_;

public class AcceloFilter<E extends AcceloEntity<E>>
{
	public static final String ALL = "_ALL";

	private Expression expression = null;

	private Search search = null;

	private boolean refreshCache = false;

	/**
	 * Limits the no. of rows returned during a query.
	 * 
	 * We default to 50 (a single page) unless the caller explicitly over-rides the default.
	 */
	private int limit = 50;

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


	public Search search(String searchValue)
	{
		if (expression != null)
			throw new AcceloException("You may not combine filters and searches");

		this.search = new Search(searchValue);

		return this.search;
	}

	public AcceloFilter<E> and(Expression child) // throws AcceloException
	{
		this.expression = new And(this.expression, child);

		return this;
	}

	public AcceloFilter<E> or(Expression child)
	{
		this.expression = new Or(this.expression, child);

		return this;
	}
	
	
	public Expression eq(FilterField<E, Integer> field, int operand)
	{
		return new Eq<E>(field, operand);
	}
	
	public Expression eq(FilterField<E, String> field, String operand)
	{
		return new Eq<E>(field, operand);
	}
	
	public Expression eq(FilterField<E, String> field, AgainstType_ operand)
	{
		return new Eq<E>(field, operand.getName());
	}

	
	public Expression eq(FilterField<E, String[]> field, String[] operand)
	{
		return new Eq<E>(field, operand);
	}
	
	public Expression eq(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new Eq<E>(field, operand);
	}
	
	public Expression before(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new Before<E>(field, operand);
	}
	
	public Expression after(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new After<E>(field, operand);
	}

	
	public Expression afterOrEq(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new AfterOrEq<E>(field, operand);
	}
	
	public Expression against(AgainstType_ type, Integer operand)
	{
		return new Against(type, operand);
	}
	
	public Expression against(AgainstType_ type, Integer... operand)
	{
		return new Against(type, operand);
	}
	
	
	public Expression empty(FilterField<E, LocalDate> dateField)
	{
		return new Empty<E>(dateField);
	}

	
	/**
	 * Use this method to invalid any cache data associated with this filter.
	 * This will force the system to re-fetch the data from the accelo servers.
	 */
	public void refreshCache()
	{
		this.refreshCache = true;
	}

	public boolean isRefreshCache()
	{
		return refreshCache;
	}

	/**
	 * Returns the accelo json expression for this filter.
	 * 
	 * "_filters": { "_OR" : { "email": "pepper@test.com", "email" :
	 * "salt@test.com" } }
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
		return toJson().replaceAll("\n", " ");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + limit;
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
		@SuppressWarnings("unchecked")
		AcceloFilter<E> other = (AcceloFilter<E>) obj;
		if (expression == null)
		{
			if (other.expression != null)
				return false;
		}
		else if (!expression.equals(other.expression))
			return false;
		if (limit != other.limit)
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
	 * 
	 * @return
	 */
	public boolean isIDFilter()
	{
		boolean isIDFilter = false;

		if (expression != null && expression instanceof Eq)
		{
			@SuppressWarnings("unchecked")
			Eq<E> simple = (Eq<E>) expression;
			if (simple.isFieldName("id"))
				isIDFilter = true;
		}

		return isIDFilter;
	}

	public void limit(int limit)
	{
		this.limit = limit;
	}

	public int getLimit()
	{
		return this.limit;
	}


	
}