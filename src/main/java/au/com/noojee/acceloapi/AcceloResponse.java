package au.com.noojee.acceloapi;

import au.com.noojee.acceloapi.dao.AcceloResponseMeta;

public abstract class AcceloResponse<T>  extends AcceloResponseMeta<T>
{
	// The deserialized entity.
	T response;
	
	public AcceloResponse()
	{
		
	}

	public T getEntity()
	{
		return response;
	}


	@Override
	public String toString()
	{
		return "AcceloResponse [ response=" + response + super.toString() + "]";
	}

}
