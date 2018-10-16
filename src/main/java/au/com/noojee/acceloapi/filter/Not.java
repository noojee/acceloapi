package au.com.noojee.acceloapi.filter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

class Not extends Expression
{

	@Override
	public Expression copy()
	{
		// a no op at this point.
		return this;
	}

	
	@Override
	public String toJson()
	{
		throw new RuntimeException("Not is not supported :)");
	}
	
	@Override
	@SuppressFBWarnings
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
