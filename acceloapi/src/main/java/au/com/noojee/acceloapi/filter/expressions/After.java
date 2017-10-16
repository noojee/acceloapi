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

}