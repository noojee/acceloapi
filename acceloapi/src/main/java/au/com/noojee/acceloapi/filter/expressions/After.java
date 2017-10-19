package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;

public class After extends Expression
{

	private String fieldName;
	private LocalDate operand;

	public After(String fieldName, LocalDate localDate)
	{
		this.fieldName = fieldName;
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
		After other = (After) obj;
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