package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.dao.AcceloList;

public abstract class AcceloResponseList<T>  implements AcceloList<T>
{
	private Meta meta;
	private List<T> response;
	
	
	@Override
	public List<T> getList()
	{
		// Guaranteed to return a list.
		if (response == null)
			response = new ArrayList<>();
		return response;
	}
	
	


	@Override
	public String toString()
	{
		return "AcceloResponse [ response=" + response + " meta=" + meta + "]";
	}
	
	
	public String getStatusMessage()
	{
		return meta.getStatusMessage();
	}

	public boolean isOK()
	{
		return meta.isOK();
	}

}
