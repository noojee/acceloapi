package au.com.noojee.acceloapi;

/**
 * Returned by Accelo when an error code 300 or greater is returned.
 * 
 * @author bsutton
 *
 */
public class AcceloErrorResponse
{
	private Meta meta;
	
	private HTTPResponse httpResponse;
	
	public void setHttpResponse(HTTPResponse httpResponse)
	{
		this.httpResponse = httpResponse;
	}
	
	public String getMessage()
	{
		return meta.message;
	}
	
	@Override
	public String toString()
	{
		return httpResponse.toString();
	}
	
	boolean isBadGateway()
	{
		return httpResponse.getResponseCode() == 502;
	}
}
