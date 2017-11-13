package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.entities.AcceloEntity;

public class AcceloResponseList<E extends AcceloEntity<E>> extends  AcceloAbstractResponseList<E>
{
	// Stores a list of AcceloEntities. 
	private List<E> response;
	
	
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
		return "AcceloResponseList [ response=" + response + super.toString() + "]";
	}
	
	

}
