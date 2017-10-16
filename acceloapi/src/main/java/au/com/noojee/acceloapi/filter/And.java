package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.filter.expressions.Expression;

public class And extends Expression
{

	private Expression parent;
	private Expression child;

	public And(Expression parent, Expression child)
	{
		super();
		this.parent = parent;
		this.child = child;
	}

	@Override
	public String toJson()
	{
		String expression = "\"_AND\": {\n" + parent.toJson() + "," + child.toJson() + "\n}";

		return expression;

	}
}
