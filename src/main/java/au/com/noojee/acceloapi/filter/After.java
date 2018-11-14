package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;

class After<E extends AcceloEntity<E>, DT> extends Expression
{

	private FilterField<E,DT> field;
	private DT operand;

	public After(FilterField<E, DT> field, DT localDate)
	{
		this.field = field;
		this.operand = localDate;
	}

	@Override
	public Expression copy()
	{
		return new After<>(this.field, operand);
	}

	@Override
	public String toJson()
	{
		String nameAndOperator = this.field.getFieldName() + "_after";

		String json = "\"" + nameAndOperator + "\": [";
		
		if (operand instanceof LocalDate)
			json += "\"" + formatDateAsFilterOperand((LocalDate)operand) + "\"";
		else
			json += "\"" + formatDateTimeAsFilterOperand((LocalDateTime)operand) + "\"";

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
		After other = (After) obj;
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