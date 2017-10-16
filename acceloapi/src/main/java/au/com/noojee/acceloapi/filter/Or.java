package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.filter.expressions.Expression;

public class Or extends Expression
{

	private Expression parent;
	private Expression child;

	public Or(Expression parent, Expression child)
	{
		super();
		this.parent = parent;
		this.child = child;
	}

	@Override
	public String toJson()
	{
		String expression = "\"_OR\": {\n" + parent.toJson() + "," + child.toJson() + "\n}";

		return expression;

	}

}
