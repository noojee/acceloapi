package au.com.noojee.acceloapi;

public class AcceloException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	AcceloErrorResponse error = null;

	public AcceloException(String message)
	{
		super(message);
	}

	public AcceloException(Throwable e)
	{
		super(e);
	}

	public AcceloException(AcceloErrorResponse error)
	{
		this.error = error;
	}
	
	@Override
	public String toString()
	{
		String response;
		if (error == null)
			response = super.toString();
		else
			response = error.toString();
		return response;
			
	}

}
