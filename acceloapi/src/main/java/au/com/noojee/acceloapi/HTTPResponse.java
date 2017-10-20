package au.com.noojee.acceloapi;

import java.io.StringReader;

import com.google.gson.Gson;


public class HTTPResponse
{
	int responseCode;
	String responseMessage;
	String responseBody;

	public HTTPResponse(int responseCode, String responseMessage, String responseBody)
	{
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseBody = responseBody;
	}

	public <T> T parseBody(Class<T> clazz) throws AcceloException
	{
		T entity;
		Gson gson = new Gson();
		if (responseCode < 300)
			entity = gson.fromJson(new StringReader(this.responseBody), clazz);
		else
		{
			AcceloErrorResponse error = gson.fromJson(new StringReader(this.responseBody),
					AcceloErrorResponse.class);

			error.setHttpResponse(this);
			// oops something bad happened.
			throw new AcceloException(error);

		}
		return entity;

	}

	@Override
	public String toString()
	{
		return "HttpResponse [responseCode=" + responseCode + ", responseMessage=" + responseMessage
				+ ", responseBody=" + responseBody + "]";
	}

}