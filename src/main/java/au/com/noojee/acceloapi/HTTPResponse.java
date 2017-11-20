package au.com.noojee.acceloapi;

import java.io.StringReader;

import au.com.noojee.acceloapi.dao.gson.GsonForAccelo;

public class HTTPResponse
{
	private int responseCode;
	private String responseMessage;
	private String responseBody;

	public HTTPResponse(int responseCode, String responseMessage, String responseBody)
	{
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseBody = responseBody;
	}
	
	public int getResponseCode()
	{
		return responseCode;
	}

	public String getResponseMessage()
	{
		return responseMessage;
	}
	
	String getResponseBody()
	{
		return responseBody;
	}



	public <T> T parseBody(Class<T> clazz) throws AcceloException
	{
		T entity;
		if (responseCode < 300)
			entity = GsonForAccelo.fromJson(new StringReader(this.responseBody), clazz);
		else
		{
			AcceloErrorResponse error;
			try
			{
				error = GsonForAccelo.fromJson(new StringReader(this.responseBody), AcceloErrorResponse.class);
			}
			catch (Exception e)
			{
				// response body wasn't json so just create an empty error
				// object.
				error = new AcceloErrorResponse();
			}

			error.setHttpResponse(this);
			// oops something bad happened.
			throw new AcceloException(error);

		}
		return entity;

	}

	@Override
	public String toString()
	{
		return "HttpResponse [responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", responseBody="
				+ responseBody + "]";
	}


}