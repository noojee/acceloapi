package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.generator.FilterField;

public class Eq<E extends AcceloEntity<E>> extends Expression
{

	private String fieldName;
	private List<String> operands = new ArrayList<>();

	
	public Eq(FilterField<E, Integer> field, int operand)
	{
		this.fieldName = field.getFieldName();
		this.operands.add("" + operand);
	}

	public Eq(FilterField<E, String> field, String operand)
	{
		this.fieldName = field.getFieldName();
		this.operands.add(operand);
	}

	public Eq(FilterField<E, String[]> field, String[] operand)
	{
		this.fieldName = field.getFieldName();
		this.operands.addAll(Arrays.asList(operand));
	}

	public Eq(FilterField<E, LocalDate> field, LocalDate operand)
	{
		this.fieldName = field.getFieldName();
		
		// HACK Accelo Doesn't support comparison of a date against '0'. Using (\"date_before\", Expression.DATEZERO) is accelos recommended hack.;
		if (operand == Expression.DATEZERO)
			this.fieldName = fieldName + "_before";

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
		@SuppressWarnings("unchecked")
		Eq<E> other = (Eq<E>) obj;
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