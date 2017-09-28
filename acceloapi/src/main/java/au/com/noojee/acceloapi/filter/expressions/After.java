package au.com.noojee.acceloapi.filter.expressions;

import java.util.Date;

public class After implements Expression
{

	private String fieldName;
	private Date operand;

	public After(String fieldName, Date operand)
	{
		this.fieldName = fieldName;
		this.operand = operand;
	}

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