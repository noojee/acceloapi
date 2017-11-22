package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;

class Before<E extends AcceloEntity<E>> extends Expression
{

	private FilterField<E, LocalDate> field;
	private LocalDate operand;

	public Before(FilterField<E, LocalDate> field, LocalDate localDate)
	{
		this.field = field;
		this.operand = localDate;
	}

	
	@Override
	public Expression copy()
	{
		Before<E> before = new Before<>(this.field, this.operand);
		
		return before;
	}

	@Override
	public String toJson()
	{
		String nameAndOperator = this.field.getFieldName() + "_before";

		String json = "\"" + nameAndOperator + "\": [";
		json += "\"" + formatDateAsFilterOperand(operand) + "\"";

		json += "]";

		return json;

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
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
		@SuppressWarnings("rawtypes")
		Before other = (Before) obj;
		if (field == null)
		{
			if (other.field != null)
				return false;
		}
		else if (!field.equals(other.field))
			return false;
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