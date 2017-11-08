package au.com.noojee.acceloapi.filter.expressions;

public class Not extends Expression
{

	@Override
	public String toJson()
	{
		throw new RuntimeException("Not is not supported :)");
	}
	
	@Override
	public boolean equals(Object e)
	{
		return true;
	}

	@Override
	public int hashCode()
	{
		return 0;
	}

}
