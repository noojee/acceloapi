package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.OrderByField.Order;
import au.com.noojee.acceloapi.entities.types.AgainstType;

public class AcceloFilter<E extends AcceloEntity<E>>
{
	public static Logger logger = LogManager.getLogger();
	public static final String ALL = "_ALL";
	
	// Used by limit(int) to remove any limit on the
	// no. of rows returned.
	public static final int UNLIMITED = -1;

	private Optional<Expression> expression = Optional.empty();

	private Optional<Search> search = Optional.empty();

	private boolean refreshCache = false;

	public AcceloFilter<E> copy()
	{
		AcceloFilter<E> filter = new AcceloFilter<>();

		this.expression.ifPresent(e -> filter.expression = Optional.of(e.copy()));
		this.expression = this.expression.map(Expression::copy);
		// this.search = this.search.map(Search::copy);/
		this.search.ifPresent(s -> filter.search = Optional.of(s.copy()));
		filter.refreshCache = this.refreshCache;
		filter.limit = this.limit;
		filter.offset = this.offset;
		filter.orderBy = this.orderBy.map(OrderBy::copy);
		return filter;
	}

	/**
	 * Limits the no. of pages returned during a query. A page can contain up to 50 rows. We default to 1 page (50 rows)
	 * unless the caller explicitly over-rides the default.
	 */
	private int limit = 1;

	/**
	 * Used with limit to iterate through a large list of entities. Defaults to 0 so we get the first page. The offset
	 * is the page to start from (0 based). Increment the offset each time you call getByFilter to move forward a page
	 * at a time.
	 */
	private int offset = 0;

	/**
	 * List of order by clauses.
	 */
	// private List<OrderBy<E>> orderByList = new ArrayList<>();

	private Optional<OrderBy> orderBy = Optional.empty();

	public void where(Search search) throws AcceloException
	{
		if (expression.isPresent())
			throw new AcceloException("You may not combine filters and searches");

		this.search = Optional.of(search);

	}

	public AcceloFilter<E> where(Expression expression) throws AcceloException
	{
		if (search.isPresent())
			throw new AcceloException("You may not combine filters and searches");

		this.expression = Optional.of(expression);

		return this;
	}

	public void orderBy(FilterField<E, Integer> field, Order order)
	{
		// this.orderByList.add(new OrderBy<E>(field, order));
		this.orderBy = Optional.of(new OrderBy<>(field, order));

	}

	public Search search(String searchValue)
	{
		if (expression.isPresent())
			throw new AcceloException("You may not combine filters and searches");

		this.search = Optional.of(new Search(searchValue));

		return this.search.orElseThrow(() -> new IllegalStateException());
	}
	
	public AcceloFilter<E> and(Expression child) throws IllegalStateException
	{
		
		this.expression = Optional.of(new And(this.expression.orElseThrow(() -> new IllegalStateException()), child));

		return this;
	}

	public AcceloFilter<E> or(Expression child)
	{
		this.expression = Optional.of(new Or(this.expression.orElseThrow(() -> new IllegalStateException()), child));

		return this;
	}

	public Expression eq(FilterField<E, Integer> field, int operand)
	{
		return new Eq<>(field, operand);
	}

	public Expression eq(FilterField<E, String> field, String operand)
	{
		return new Eq<>(field, operand);
	}

	public Expression eq(FilterField<E, String> field, AgainstType operand)
	{
		return new Eq<>(field, operand.getName());
	}

	public <T extends Enum<T>> Expression eq(FilterField<E, T> field, T operand)
	{
		return new Eq<>(field, operand);
	}

	public Expression eq(FilterField<E, String[]> field, String[] operand)
	{
		return new Eq<>(field, operand);
	}

	public Expression eq(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new Eq<>(field, operand);
	}

	public Expression before(FilterField<E, LocalDateTime> field, LocalDateTime operand)
	{
		return new Before<>(field, operand);
	}

	public Expression before(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new Before<>(field, operand);
	}

	public Expression after(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new After<>(field, operand);
	}

	public Expression afterOrEq(FilterField<E, LocalDate> field, LocalDate operand)
	{
		return new AfterOrEq<>(field, operand);
	}
	
	
	public Expression eq(FilterField<E, LocalDateTime> field, LocalDateTime operand)
	{
		return new Eq<>(field, operand);
	}

	public Expression after(FilterField<E, LocalDateTime> field, LocalDateTime operand)
	{
		return new After<>(field, operand);
	}

	public Expression afterOrEq(FilterField<E, LocalDateTime> field, LocalDateTime operand)
	{
		return new AfterOrEq<>(field, operand);
	}


	public Expression against(AgainstType type, Integer operand)
	{
		return new Against(type, operand);
	}

	public Expression against(AgainstType type, Integer... operand)
	{
		return new Against(type, operand);
	}

	public Expression empty(FilterField<E, LocalDate> dateField)
	{
		return new Empty<>(dateField);
	}

	public Expression greaterThan(FilterField<E, Integer> field, Integer operand)
	{
		return new GreaterThan<>(field, operand);
	}

	public Expression greaterThan(FilterField<E, String> field, String operand)
	{
		return new GreaterThan<>(field, operand);
	}

	public Expression lessThan(FilterField<E, Integer> field, Integer operand)
	{
		return new LessThan<>(field, operand);
	}

	public Expression lessThan(FilterField<E, String> field, String operand)
	{
		return new LessThan<>(field, operand);
	}

	public Expression greaterThanOrEq(FilterField<E, Integer> field, Integer operand)
	{
		return new GreaterThanOrEq<>(field, operand);
	}

	public Expression greaterThanOrEq(FilterField<E, String> field, String operand)
	{
		return new GreaterThanOrEq<>(field, operand);
	}

	public Expression lessThanOrEq(FilterField<E, Integer> field, Integer operand)
	{
		return new LessThanOrEq<>(field, operand);
	}

	public Expression lessThanOrEq(FilterField<E, String> field, String operand)
	{
		return new LessThanOrEq<>(field, operand);
	}

	/**
	 * Use this method to invalid any cache data associated with this filter. This will force the system to re-fetch the
	 * data from the accelo servers.
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
	 * Returns the accelo json expression for this filter. "_filters": { "_OR" : { "email": "pepper@test.com", "email" :
	 * "salt@test.com" } }
	 * 
	 * @param filterMap
	 * @return
	 * @return
	 */
	public String toJson()
	{
		Optional<String> result = Optional.empty();

		// this.expression.ifPresent(e -> filter.expression = Optional.of(e.copy()));
		result = expression.map(exp ->
			{

				String json = "\"_filters\": {\n";

				json += exp.toJson();

				json += this.orderBy.map(ob -> "," + ob.toJson()).orElse("");

				json += "}";
				return json;
			});

		if (!result.isPresent())
		{
			result = search.map(s ->
				{
					String json = "\"_search\": ";
					json += "\"" + s.getOperand() + "\"";

					return json;
				});
		}

		return result.orElse("");
	}

	@Override
	public String toString()
	{
		return toJson().replaceAll("\n", " ") + " limit: " + this.limit + " offset: " + this.offset;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + limit;
		result = prime * result + offset;
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
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
		@SuppressWarnings("rawtypes")
		AcceloFilter other = (AcceloFilter) obj;
		if (expression == null)
		{
			if (other.expression != null)
				return false;
		}
		else if (!expression.equals(other.expression))
			return false;
		if (limit != other.limit)
			return false;
		if (offset != other.offset)
			return false;
		if (orderBy == null)
		{
			if (other.orderBy != null)
				return false;
		}
		else if (!orderBy.equals(other.orderBy))
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
		return expression.filter(Eq.class::isInstance).map(Eq.class::cast).filter(ex -> ex.isFieldName("id"))
				.isPresent();
	}

	/**
	 * Sets the maximum no. of pages (default is 50 entities per page) the filter will return.
	 * @param limit
	 */
	public void limit(int limit)
	{
		this.limit = limit;
	}

	// removes the limit on the no. of entities returned.
	// becareful because you can crash accelo.
	public void noLimit()
	{
		limit(AcceloFilter.UNLIMITED);
	}


	/**
	 * @return the maximum no. of pages (default is 50 entities per page) the filter will return.
	 */
	private int getLimit()
	{
		return this.limit;
	}

	public void offset(int offset)
	{
		this.offset = offset;
	}

	public int getOffset()
	{
		return this.offset;
	}

	public void showHashCode()
	{
		logger.error("Filter hashcode=" + this.hashCode());

		logger.error("Expression: " + expression.hashCode());

		logger.error("OrderBy: " + orderBy.hashCode());

	}

	// returns true of no. of entities retrieved is less than the
	// limit imposed by this filter.
	public boolean belowLimit(int entitiesRetrieved)
	{
		 return limit == UNLIMITED || (entitiesRetrieved < (getLimit() * AcceloApi.PAGE_SIZE));
	}


}