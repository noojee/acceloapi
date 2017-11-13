package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.Meta;

public abstract class AcceloResponseMeta<E>
{
	private Meta meta;
	
	public String getStatusMessage()
	{
		return meta.getStatusMessage();
	}

	public boolean isOK()
	{
		return meta.isOK();
	}
	
	public String toString()
	{
		return " meta=" + meta; 
	}

}
