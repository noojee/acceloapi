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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import au.com.noojee.acceloapi.dao.gson.GsonForAccelo;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class AcceloApi
{
	private Logger logger = LogManager.getLogger(this.getClass());
	public static final int PAGE_SIZE = 50;

	static private AcceloApi self = null;;
	/**
	 * The base url to the Accelo api crm e.g. "https://myorg.api.accelo.com"
	 */
	private String baseURL = null;

	private String accessToken = null;

	public enum HTTPMethod
	{
		GET, POST, PUT, DELETE
	}

	static synchronized public AcceloApi getInstance()
	{
		if (self == null)
			self = new AcceloApi();

		return AcceloApi.self;
	}

	private AcceloApi()
	{
		System.setProperty("http.maxConnections", "8"); // set globally only
														// once
	}

	public String getBaseURL()
	{
		return baseURL;
	}

	/**
	 * Pulls every matching entity. Be careful! you could run out of memory and slam Accelo! You can only use this
	 * method of Accelo returns a list of entities. This method will throw an exception if you try to make a call that
	 * causes Accelo to return a single entity (e.g. don't try to get a contact by id).
	 * 
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @return all entities for the given endpoint subject to the filters limit.
	 */
	public <E extends AcceloEntity<E>> List<E> getAll(EndPoint endPoint, AcceloFilter<E> filterMap,
			AcceloFieldList fieldList,
			Class<? extends AcceloAbstractResponseList<E>> clazz)
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

	public <E extends AcceloEntity<E>> E get(EndPoint endPoint, AcceloFilter<E> filterMap,
			AcceloFieldList fieldList,
			Class<? extends AcceloResponse<E>> clazz)
	{
		AcceloResponse<E> response;
		try
		{
			response = get(endPoint.getURL(), filterMap, fieldList, clazz, 0);

		}
		catch (MalformedURLException e)
		{
			throw new AcceloException(e);
		}

		return response.getEntity();
	}

	public <E extends AcceloEntity<E>, L extends AcceloAbstractResponseList<E>> List<E> getAll(URL url,
			AcceloFilter<E> filter, AcceloFieldList fieldList, Class<L> responseClass)
	{
		List<E> entities = new ArrayList<>();
		boolean more = true;
		int page = filter.getOffset();

		while (more && entities.size() < (filter.getLimit() * PAGE_SIZE))
		{
			L responseList = get(url, filter, fieldList, responseClass, page);

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
	 * Send a request to get a single entity or the 'nth' page of entities. The first page is page 0.
	 * 
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @param pageNo the Page to return
	 * @return
	 */

	public <E extends AcceloEntity<E>, R> R get(URL url, AcceloFilter<E> filterMap, AcceloFieldList fieldList,
			Class<R> clazz, int pageNo)
	{
		HTTPResponse response = get(url, filterMap, fieldList, pageNo);
		return response.parseBody(clazz);
	}

	public <E extends AcceloEntity<E>, R> String getRaw(EndPoint endPoint, AcceloFilter<E> filter,
			AcceloFieldList fieldList, Class<R> clazz)
	{
		HTTPResponse response;
		try
		{
			String json = buildJsonBody(HTTPMethod.GET, GsonForAccelo.toJson(fieldList), filter.toJson());

			response = _request(HTTPMethod.POST, endPoint.getURL(), json);
		}
		catch (MalformedURLException e)
		{
			throw new AcceloException(e);
		}
		return response.getResponseBody();
	}

	/**
	 * Send a request to get a single entity or the 'nth' page of entities. The first page is page 0.
	 * 
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param pageNo the Page to return
	 * @return
	 */

	private <E extends AcceloEntity<E>> HTTPResponse get(URL url, AcceloFilter<E> filterMap, AcceloFieldList fieldList,
			int pageNo)
	{
		String fields = GsonForAccelo.toJson(fieldList);
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
	 * Send an entity to Accelo. FieldValues is a map of name value pair that constitute the entity that we are pushing.
	 */
	public <E> E insert(EndPoint endPoint, // AcceloFieldValues fieldNameValues
			// AcceloEntity<?> entity

			String jsonFieldValues, Class<E> clazz)
	{
		// String json = buildJsonBody(method, fieldNameValues);

		// looks like you can't post fields via json so we need to add data to
		// the url.

		// String urlArgs = fieldNameValues.buildUrlArgs();

		URL completeUrl;
		try
		{
			completeUrl = new URL(endPoint.getURL().toExternalForm());
		}
		catch (MalformedURLException e)
		{
			throw new AcceloException(e);
		}

		// String fieldValues = fieldNameValues.formatAsJson();

		HTTPResponse response = _request(HTTPMethod.POST, completeUrl, jsonFieldValues);

		return response.parseBody(clazz);
	}

	/*
	 * Updates an existing entity . FieldValues is a map of name value pair that constitute the entity that we are
	 * pushing.
	 * @entityId - the accelo id of the entity to be updated.
	 */
	public <E> E update(EndPoint endPoint, int id,
			String jsonFieldValues, Class<E> clazz)
	{
		URL completeUrl;
		try
		{
			completeUrl = new URL(endPoint.getURL(id).toExternalForm());
		}
		catch (MalformedURLException e)
		{
			throw new AcceloException(e);
		}

		logger.error("Updating: url=" + completeUrl + " jsonFieldValues=" + jsonFieldValues);
		HTTPResponse response = _request(HTTPMethod.PUT, completeUrl, jsonFieldValues);
		if (response.getResponseCode() != 200)
			throw new AcceloException("Update faieled for endPoint " + endPoint + " id=" + id);

		return response.parseBody(clazz);
	}

	public HTTPResponse delete(EndPoint endPoint, int id)
	{
		URL completeUrl = buildURL(endPoint, id);
		logger.error("Deleting: url=" + completeUrl);
		HTTPResponse response = _request(HTTPMethod.DELETE, completeUrl, null);
		if (response.getResponseCode() != 200)
			throw new AcceloException("Delete faieled for endPoint " + endPoint + " id=" + id + " Reason:" + response.getResponseMessage());

		logger.error("Deleted: url=" + completeUrl);

		return response;
	}

	private URL buildURL(EndPoint endPoint, int id)
	{
		try
		{
			return new URL(endPoint.getURL(id).toExternalForm());
		}
		catch (MalformedURLException e)
		{
			// should never happen
			throw new AcceloException(e);
		}
	}

	/**
	 * Returns a raw response string.
	 */
	public HTTPResponse _request(HTTPMethod method, URL url, String jsonArgs)
	{
		HTTPResponse response = null;

		try
		{

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
			catch (IOException e)
			{
				try (InputStream streamError = connection.getErrorStream())
				{
					error = fastStreamReader(streamError);
				}
			}

			// Read the response.
			if (responseCode < 300)
			{
				// logger.error("Response body" + body);
				response = new HTTPResponse(responseCode, connection.getResponseMessage(), body);
			}
			else
			{

				response = new HTTPResponse(responseCode, connection.getResponseMessage(), error);

				logger.error(response);
				logger.error("EndPoint responsible for error: " + method.toString() + " " + url);
				logger.error("Subumitted body responsible for error: " + jsonArgs);
			}
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response;

	}

	String fastStreamReader(InputStream inputStream)
	{
		if (inputStream != null)
		{
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[4000];
			int length;
			try
			{
				while ((length = inputStream.read(buffer)) != -1)
				{
					result.write(buffer, 0, length);
				}
				return result.toString(StandardCharsets.UTF_8.name());

			}
			catch (IOException e)
			{
				throw new AcceloException(e);
			}

		}
		return "";
	}

	/**
	 * @param secret - use AcceloSecret.load
	 */
	public void connect(AcceloSecret secret)
	{
		this.baseURL = "https://" + secret.getFQDN() + "/api/v0/";

		try
		{
			// Enable to debug https connection
			// sun.util.logging.PlatformLogger.getLogger("sun.net.www.protocol.http.HttpURLConnection")
			// .setLevel(sun.util.logging.PlatformLogger.Level.ALL);

			String authURL = "https://" + secret.getFQDN() + "/oauth2/v0/";

			// String resource = "authorize";
			String resource = "token";

			URL url = new URL(authURL + resource);

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			Map<String, String> arguments = new HashMap<>();
			arguments.put("request_type", "code");
			arguments.put("grant_type", "client_credentials");
			arguments.put("client_id", secret.getClientId());
			arguments.put("client_secret", secret.getClientSecret());

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

}
