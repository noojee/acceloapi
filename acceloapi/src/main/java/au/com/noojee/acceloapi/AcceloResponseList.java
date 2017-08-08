package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

public abstract class AcceloResponseList<T> 
{
	private Meta meta;
	private List<T> response;
	
	
	public List<T> getList()
	{
		// Guarenteed to return a list.
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
