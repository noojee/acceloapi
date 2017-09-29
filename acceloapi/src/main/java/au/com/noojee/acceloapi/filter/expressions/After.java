package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;

public class After implements Expression
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

		// if (simpleMatch.getOperands().size() != 1)
		// throw new IllegalArgumentException("The BEFORE operator takes
		// only a single operand.");

		String nameAndOperator = fieldName + "_after";

		String json = "\"" + nameAndOperator + "\": [";
		json += "\"" + formatDateAsFilterOperand(operand) + "\"";

		json += "]";

		return json;

	}

}