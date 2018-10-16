package au.com.noojee.acceloapi;

/**
 * Returned by Accelo when an error code 300 or greater is returned.
 * 
 * @author bsutton
 *
 */
public class AcceloErrorResponse
{
	@SuppressWarnings("unused")
	private Meta meta;
	
	private HTTPResponse httpResponse;
	
	public void setHttpResponse(HTTPResponse httpResponse)
	{
		this.httpResponse = httpResponse;
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
