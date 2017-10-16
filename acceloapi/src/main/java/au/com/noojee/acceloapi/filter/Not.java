package au.com.noojee.acceloapi.filter;

import au.com.noojee.acceloapi.filter.expressions.Expression;

public class Not extends Expression
{

	@Override
	public String toJson()
	{
		throw new RuntimeException("Not is not supported :)");
	}

}
