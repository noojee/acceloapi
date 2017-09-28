package au.com.noojee.acceloapi.filter.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Compound implements Expression
{

	private String fieldName;
	private List<Expression> expressions = new ArrayList<>();

	public Compound(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public Compound(String fieldName, Expression match)
	{
		this.fieldName = fieldName;
		this.expressions.add(match);
	}

	public Compound(String fieldName, Expression[] match)
	{
		this.fieldName = fieldName;
		this.expressions.addAll(Arrays.asList(match));
	}

	public Compound add(Expression match)
	{
		this.expressions.add(match);
		return this;
	}

	public String toJson()
	{
		String json = "";

		json += "\"" + fieldName + "\": [{";

		boolean firstOperand = true;
		for (Expression expression : expressions)
		{
			if (firstOperand)
				firstOperand = false;
			else
				json += ",";

			json += expression.toJson();
		}
		json += "}]";

		return json;

	}
}