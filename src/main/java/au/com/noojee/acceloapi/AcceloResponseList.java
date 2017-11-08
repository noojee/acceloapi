package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.dao.AcceloList;
import au.com.noojee.acceloapi.entities.AcceloEntity;

public abstract class AcceloResponseList<E extends AcceloEntity<E>>  implements AcceloList<E>
{
	private Meta meta;
	
	// Stores a list of AcceloEntities. 
	private List<E> response;
	
	
	@Override
	public List<E> getList()
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
