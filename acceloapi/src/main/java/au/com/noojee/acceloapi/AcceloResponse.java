package au.com.noojee.acceloapi;

public abstract class AcceloResponse<T>  
{
	private Meta meta;
	
	// The deserialized entity.
	T response;
	
	public AcceloResponse()
	{
		
	}
	
	void setMeta(Meta meta)
	{
		this.meta = meta;
	}
	
	public T getEntity()
	{
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
