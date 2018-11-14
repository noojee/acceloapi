package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.Meta;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class AcceloResponseMeta<E>
{
	@SuppressFBWarnings
	private Meta meta;
	
	public String getStatusMessage()
	{
		return meta.getStatusMessage();
	}

	public boolean isOK()
	{
		return meta.isOK();
	}
	
	@Override
	public String toString()
	{
		return " meta=" + meta; 
	}

}
