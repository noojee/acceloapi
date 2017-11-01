package au.com.noojee.acceloapi;

public class Meta
{
	
	String status;
	String more_info;
	String message;
	@Override
	public String toString()
	{
		return "Meta [status=" + status + ", more_info=" + more_info + ", message=" + message + "]";
	}
	public String getStatusMessage()
	{
		return status + ":" + message;
	}
	public boolean isOK()
	{
		return status.compareToIgnoreCase("ok") == 0;
	}
}