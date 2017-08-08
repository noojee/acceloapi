package au.com.noojee.acceloapi;

import java.io.StringReader;

import com.google.gson.Gson;

/**
 * Use this method to fetch data from accelo.
 * 
 * 
 * @param method
 * @param url
 * @param urlArgs
 * @param fieldList
 * @param clazz
 * @return
 * @throws IOException
 * @throws AcceloException
 */
// public <T, E extends AcceloResponse> T pull(HTTPMethod method, URL url,
// AcceloFilter filterMap,
// AcceloFieldList fieldList, Class<E> clazz) throws IOException,
// AcceloException
// {
// String fields = fieldList.formatAsJson();
// String filters = filterMap.formatAsJson();
// int a = 1;
//
// String json = buildJsonBody(method, fields, filters);
//
// HttpResponse response = _request(HTTPMethod.POST, url, json);
//
// return response.parseBody(clazz);
//
// }

class HttpResponse
{
	int responseCode;
	String responseMessage;
	String responseBody;

	public HttpResponse(int responseCode, String responseMessage, String responseBody)
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