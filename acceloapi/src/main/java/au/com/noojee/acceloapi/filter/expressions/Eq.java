package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Eq implements Expression
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
		this.operands.add("" + operand);
	}

	public Eq(String fieldName, String[] operand)
	{
		this.fieldName = fieldName;
		this.operands.addAll(Arrays.asList(operand));
	}

	public Eq(String fieldName, LocalDate operand)
	{
		this.fieldName = fieldName;
		this.operands.add(formatDateAsFilterOperand(operand));
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

}