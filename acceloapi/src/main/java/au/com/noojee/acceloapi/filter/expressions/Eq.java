package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.noojee.acceloapi.AcceloException;

public class Eq extends Expression
{

	private String fieldName;
	private List<String> operands = new ArrayList<>();

	public Eq(String fieldName, int operand)
	{
		this.fieldName = fieldName;
		this.operands.add("" + operand);
	}

	public Eq(String fieldName, String operand)
	{
		this.fieldName = fieldName;
		this.operands.add(operand);
	}

	public Eq(String fieldName, String[] operand)
	{
		this.fieldName = fieldName;
		this.operands.addAll(Arrays.asList(operand));
	}

	public Eq(String fieldName, LocalDate operand)
	{
		// HACK Accelo Doesn't support comparison of a date against '0'. Using (\"date_before\", Expression.DATEZERO) is accelos recommended hack.;
		if (operand == Expression.DATEZERO)
			this.fieldName = fieldName + "_before";
		else
			this.fieldName = fieldName;
		this.operands.add(formatDateAsFilterOperand(operand));
	}

	public boolean isFieldName(String fieldName)
	{
		return this.fieldName.compareTo(fieldName) == 0;
	}

	@Override
	public String toJson()
	{
		String json = "\"" + fieldName + "\": [";

		boolean firstOperand = true;
		for (String operand : operands)
		{
			if (firstOperand)
				firstOperand = false;
			else
				json += ",";

			json += "\"" + operand + "\"";
		}
		json += "]";

		return json;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((operands == null) ? 0 : operands.hashCode());
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
		Eq other = (Eq) obj;
		if (fieldName == null)
		{
			if (other.fieldName != null)
				return false;
		}
		else if (!fieldName.equals(other.fieldName))
			return false;
		if (operands == null)
		{
			if (other.operands != null)
				return false;
		}
		else if (!operands.equals(other.operands))
			return false;
		return true;
	}

}