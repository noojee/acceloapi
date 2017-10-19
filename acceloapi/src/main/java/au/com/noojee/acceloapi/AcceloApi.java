package au.com.noojee.acceloapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import au.com.noojee.acceloapi.dao.AcceloList;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class AcceloApi
{
	private Logger logger = LogManager.getLogger(this.getClass());
	public static final int PAGE_SIZE = 50;

	/**
	 * The base url to the Accelo api crm e.g. "https://myorg.api.accelo.com"
	 * 
	 */
	static String baseURL = null;

	/**
	 * The accelo client id
	 */
	private static String clientID;

	/**
	 * The accelo secret
	 */
	private static String secret;

	/**
	 * The fully qualified domain name to the accelo instance.
	 */
	private static String fqdn;

	private String accessToken = null;

	public enum HTTPMethod
	{
		GET, POST, PUT
	}

	static public void setFQDN(String fqdn)
	{
		AcceloApi.baseURL = "https://" + fqdn + "/api/v0/";
		AcceloApi.fqdn = fqdn;
	}

	static public void setClientID(String clientID)
	{
		AcceloApi.clientID = clientID;

	}

	static public void setClientSecret(String secret)
	{
		AcceloApi.secret = secret;
	}

	public AcceloApi()
	{
		System.setProperty("http.maxConnections", "8"); // set globally only
														// once

	}

	/**
	 * Pulls every matching entity. Be careful! you could run out of memory and
	 * slam Accelo! You can only use this method of Accelo returns a list of
	 * entities. This method will throw an exception if you try to make a call
	 * that causes Accelo to return a single entity (e.g. don't try to get a
	 * contact by id).
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @return
	 */
//	public <T> List<T> getAll(EndPoint endPoint, AcceloFilter filterMap, AcceloFieldList fieldList,
//			Class<? extends AcceloResponseList<T>> clazz) throws IOException, AcceloException
//	{
//		return getAll(endPoint.getURL(), filterMap, fieldList, clazz);
//	}
	
	public <E> List<E> getAll(EndPoint endPoint, AcceloFilter filterMap, AcceloFieldList fieldList,
			Class<? extends AcceloList<E>> clazz) throws AcceloException
	{
		List<E> list;
		try
		{
			list = getAll(endPoint.getURL(), filterMap, fieldList, clazz);
		}
		catch (MalformedURLException e)
		{
			throw new AcceloException(e);
		}
		
		return list;
	}


	
//	public <T> List<T> getAll(URL url, AcceloFilter filterMap, AcceloFieldList fieldList,
//			Class<? extends AcceloResponseList<T>> clazz) throws IOException, AcceloException
//	{
//		List<T> entities = new ArrayList<>();
//		boolean more = true;
//		int page = 0;
//		while (more)
//		{
//			AcceloResponseList<T> responseList = get(url, filterMap, fieldList, clazz, page);
//
//			if (responseList != null)
//			{
//				List<T> entityList = responseList.getList();
//
//				// If we get less than a page we must now have everything.
//				if (entityList.size() < PAGE_SIZE)
//					more = false;
//
//				for (T entity : entityList)
//				{
//					entities.add(entity);
//				}
//				page += 1;
//			}
//
//		}
//
//		return entities;
//	}
	
	
	public <E, L extends AcceloList<E>> List<E> getAll(URL url, AcceloFilter filterMap, AcceloFieldList fieldList, Class<L> responseClass) throws AcceloException
	{
		List<E> entities = new ArrayList<>();
		boolean more = true;
		int page = 0;
		while (more)
		{
			L responseList = get(url, filterMap, fieldList, responseClass, page);

			if (responseList != null)
			{
				List<E> entityList = responseList.getList();

				// If we get less than a page we must now have everything.
				if (entityList.size() < AcceloApi.PAGE_SIZE)
					more = false;

				entities.addAll(entityList);
				page += 1;
			}

		}

		return entities;
	}



	/**
	 * Send a request to get a single entity or the 'nth' page of entities. The
	 * first page is page 0.
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @param pageNo
	 *            the Page to return
	 * @return
	 * @throws IOException
	 * @throws AcceloException
	 */

	public <R> R get(EndPoint endPoint, AcceloFilter filterMap, AcceloFieldList fieldList, Class<R> clazz, int pageNo)
			throws IOException, AcceloException
	{
		HTTPResponse response = get(endPoint.getURL(), filterMap, fieldList, pageNo);
		return response.parseBody(clazz);
	}

	/**
	 * Send a request to get a single entity or the first page of entities.
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @param page
	 *            if true then will pull a full list of matching entities by
	 *            paging through the list.
	 * @return
	 * @throws IOException
	 * @throws AcceloException
	 */
	public <R> R get(EndPoint endPoint, AcceloFilter filterMap, AcceloFieldList fieldList, Class<R> clazz)
			throws IOException, AcceloException
	{
		return get(endPoint, filterMap, fieldList, clazz, 0);
	}

	/**
	 * Send a request to get a single entity or the 'nth' page of entities. The
	 * first page is page 0.
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @param pageNo
	 *            the Page to return
	 * @return
	 * @throws IOException
	 * @throws AcceloException
	 */

	public HTTPResponse get(EndPoint endPoint, AcceloFilter filterMap, AcceloFieldList fieldList, int pageNo)
			throws IOException, AcceloException
	{
		HTTPResponse response = get(endPoint.getURL(), filterMap, fieldList, pageNo);
		return response;
	}

	/**
	 * Send a request to get a single entity or the 'nth' page of entities. The
	 * first page is page 0.
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @param pageNo
	 *            the Page to return
	 * @return
	 * @throws IOException
	 * @throws AcceloException
	 */

	public <R> R get(URL url, AcceloFilter filterMap, AcceloFieldList fieldList, Class<R> clazz, int pageNo)
			throws AcceloException
	{
		HTTPResponse response = get(url, filterMap, fieldList, pageNo);
		return response.parseBody(clazz);
	}

	/**
	 * Send a request to get a single entity or the 'nth' page of entities. The
	 * first page is page 0.
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @param pageNo
	 *            the Page to return
	 * @return
	 * @throws IOException
	 * @throws AcceloException
	 */

	private HTTPResponse get(URL url, AcceloFilter filterMap, AcceloFieldList fieldList, int pageNo)
			throws AcceloException
	{
		String fields = fieldList.formatAsJson();
		String filters = (filterMap == null) ? null : filterMap.toJson();

		String json = buildJsonBody(HTTPMethod.GET, fields, filters);
		
		HTTPResponse response = null;

		URL pagedURL;
		try
		{
			pagedURL = new URL(url + "?_page=" + pageNo + "&_limit=" + PAGE_SIZE);
			response = _request(HTTPMethod.POST, pagedURL, json);
		}
		catch (IOException e)
		{
			
			throw new AcceloException(e);
		}

		return response;

	}

	/*
	 * Send an entity to Accelo. FieldValues is a map of name value pair that
	 * constitute the entity that we are pushing.
	 */
	public <T> T insert(EndPoint endPoint, AcceloFieldValues fieldNameValues, Class<T> clazz)
			throws IOException, AcceloException
	{
		// String json = buildJsonBody(method, fieldNameValues);

		// looks like you can't post fields via json so we need to add data to
		// the url.

		String urlArgs = fieldNameValues.buildUrlArgs();

		URL completeUrl = new URL(endPoint.getURL().toExternalForm() + "?" + urlArgs);
		HTTPResponse response = _request(HTTPMethod.POST, completeUrl, null);

		return response.parseBody(clazz);
	}

	/*
	 * Updates an existing entity . FieldValues is a map of name value pair that
	 * constitute the entity that we are pushing.
	 * 
	 * @entityId - the accelo id of the entityt to be updated.
	 */
	public <T> T update(EndPoint endPoint, int entityId, AcceloFieldValues fieldNameValues, Class<T> clazz)
			throws IOException, AcceloException
	{
		// looks like you can't post fields via json so we need to add data to
		// the url.
		// TODO: I don't think this is true actually.
		String urlArgs = fieldNameValues.buildUrlArgs();

		URL completeUrl = new URL(endPoint.getURL(entityId).toExternalForm() + "?" + urlArgs);
		HTTPResponse response = _request(HTTPMethod.PUT, completeUrl, null);

		return response.parseBody(clazz);
	}

	/**
	 * Returns a raw response string.
	 * 
	 * @throws AcceloException
	 */
	private HTTPResponse _request(HTTPMethod method, URL url, String jsonArgs) throws IOException, AcceloException
	{

		HTTPResponse response;

		logger.debug(method + " url: " + url);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		// We always use post as we are using json and defining the method via
		// _method in the json data.
		connection.setRequestMethod(method.toString());
		connection.setDoOutput(true);
		connection.setAllowUserInteraction(false); // no users here so don't do
													// anything silly.

		// connection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);

		connection.connect();

		// Write the json arguments if any exist.
		if (jsonArgs != null)
		{
			logger.debug("jsonArgs: " + jsonArgs);
			try (OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"))
			{
				osw.write(jsonArgs.toString());
				osw.flush();
				osw.close();
			}

		}

		int responseCode = connection.getResponseCode();

		// 404 returns HTML so no point trying to parse it.
		if (responseCode == 404)
			throw new AcceloException("The passed url was not found" + url.toString());

		String body = "";
		String error = "";

		try (InputStream streamBody = connection.getInputStream())
		{
			body = fastStreamReader(streamBody);
		}
		catch (@SuppressWarnings("unused") IOException e)
		{
			try (InputStream streamError = connection.getErrorStream())
			{
				error = fastStreamReader(streamError);
			}
		}

		// Read the response.
		if (responseCode < 300)

			response = new HTTPResponse(responseCode, connection.getResponseMessage(), body);
		else
			response = new HTTPResponse(responseCode, connection.getResponseMessage(), error);

		return response;

	}

	String fastStreamReader(InputStream inputStream) throws IOException
	{
		if (inputStream != null)
		{
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[4000];
			int length;
			while ((length = inputStream.read(buffer)) != -1)
			{
				result.write(buffer, 0, length);
			}

			return result.toString(StandardCharsets.UTF_8.name());
		}
		return "";
	}

	// retrieve the access token.
	public void connect() throws AcceloException
	{
		try
		{

			// Enable to debug https connection
			// sun.util.logging.PlatformLogger.getLogger("sun.net.www.protocol.http.HttpURLConnection")
			// .setLevel(sun.util.logging.PlatformLogger.Level.ALL);

			if (AcceloApi.baseURL == null)
				throw new AssertionError("call AcceloApi.setBbaseURL first");
			if (AcceloApi.clientID == null)
				throw new AssertionError("call AcceloApi.setClientID first");
			if (AcceloApi.secret == null)
				throw new AssertionError("call AcceloApi.setSecret first");

			String baseURL = "https://" + AcceloApi.fqdn + "/oauth2/v0/";

			// String resource = "authorize";
			String resource = "token";

			URL url = new URL(baseURL + resource);

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			Map<String, String> arguments = new HashMap<>();
			arguments.put("request_type", "code");
			arguments.put("grant_type", "client_credentials");
			arguments.put("client_id", AcceloApi.clientID);
			arguments.put("client_secret", AcceloApi.secret);

			byte[] args = buildArgs(arguments);

			connection.setFixedLengthStreamingMode(args.length);

			logger.debug("connect");
			connection.connect();
			try (OutputStream os = connection.getOutputStream())
			{
				os.write(args);
			}

			int responseCode = connection.getResponseCode();
			logger.debug("Response: " + responseCode);
			if (responseCode < 500)
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				Gson gson = new Gson();
				Accelo accelo = gson.fromJson(in, Accelo.class);

				accessToken = accelo.getAccess_token();

				in.close();
			}
			else
				logger.debug("Invalid Response: " + responseCode);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}
	}

	public String buildUrlArgList(Map<String, String> urlArgMap)
	{
		String urlArgs = "";
		if (urlArgMap != null)
		{
			for (String key : urlArgMap.keySet())
			{

				if (urlArgs.length() > 0)
					urlArgs += "&";

				urlArgs += key + "=" + urlArgMap.get(key);

			}
		}
		return urlArgs;
	}

	String buildJsonBody(HTTPMethod method, String fields, String filters)
	{
		// build the full json string;
		String json = "";

		json = "{";
		json += "\"_method\": \"" + method + "\"";

		if (fields.length() > 0)
			json += ",\n" + fields;

		if (filters != null && filters.length() > 0)
			json += ",\n" + filters;

		json += "}";

		return json;

	}

	@SuppressWarnings("unused")
	private String buildJsonBody(HTTPMethod method, AcceloFieldValues fieldNameValues)
	{
		String json = "{";
		json += "\"_method\": \"" + method + "\"";

		String fields = fieldNameValues.formatAsJson();
		if (fields.length() > 0)
			json += ",\n" + fields;

		json += "}";

		return json;

	}

	static public byte[] buildArgs(Map<String, String> arguments)
	{
		byte[] out = null;
		try
		{
			String sj = new String();
			for (Map.Entry<String, String> entry : arguments.entrySet())
			{
				if (entry.getValue() != null)
				{
					if (sj.length() > 0)
						sj += "&";
					sj += URLEncoder.encode(entry.getKey(), "UTF-8") + "="
							+ URLEncoder.encode(entry.getValue(), "UTF-8");
				}

			}

			out = sj.toString().getBytes(StandardCharsets.UTF_8);
		}
		catch (UnsupportedEncodingException e)
		{
			// should never happen.
			e.printStackTrace();
		}

		return out;
	}

	public static LocalDate toLocalDate(long dateToSeconds)
	{
		return Instant.ofEpochSecond(dateToSeconds).atZone(ZoneId.systemDefault()).toLocalDate();

	}

	public static long toDateAsLong(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
	}

	public static Date toDate(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
