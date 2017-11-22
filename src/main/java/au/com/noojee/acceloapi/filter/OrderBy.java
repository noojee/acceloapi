package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.OrderByField.Order;

public class OrderBy<E extends AcceloEntity<E>, T extends Object>
{

	private FilterField<E, T >field;
	private Order order;

	public OrderBy(FilterField<E, T> field, Order order)
	{
		this.field = field;
		this.order = order;
	}

	public String getFieldName()
	{
		return this.field.getFieldName();
	}

	public Order getOrder()
	{
		return order;
	}

	public OrderBy<E,T> copy()
	{
		return new OrderBy<E,T>(this.field.copy(),  order);
	}

	public String toJson()
	{
		String orderBy = "order_by_" + order.name().toLowerCase();

		String json = "\"" + orderBy + "\": [";
		json += "\"" + field.getFieldName() + "\"";

		json += "]";

		return json;

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		OrderBy other = (OrderBy) obj;
		if (field == null)
		{
			if (other.field != null)
				return false;
		}
		else if (!field.equals(other.field))
			return false;
		if (order != other.order)
			return false;
		return true;
	}


	
}
