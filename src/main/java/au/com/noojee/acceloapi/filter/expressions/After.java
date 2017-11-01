package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.FilterField;

public class After<E extends AcceloEntity<E>> extends Expression
{

	private String fieldName;
	private LocalDate operand;

	public After(FilterField<E, LocalDate> field, LocalDate localDate)
	{
		this.fieldName = field.getFieldName();
		this.operand = localDate;
	}

	@Override
	public String toJson()
	{
		String nameAndOperator = fieldName + "_after";

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
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
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
		@SuppressWarnings("unchecked")
		After<E> other = (After<E>) obj;
		if (fieldName == null)
		{
			if (other.fieldName != null)
				return false;
		}
		else if (!fieldName.equals(other.fieldName))
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