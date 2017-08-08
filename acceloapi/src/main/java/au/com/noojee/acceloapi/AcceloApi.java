package au.com.noojee.acceloapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import au.com.noojee.acceloapi.entities.Accelo;

public class AcceloApi
{
	private Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * The base url to the Accelo api crm 
	 * e.g. "https://myorg.api.accelo.com"
	 * 
	 */
	static private String baseURL = null;

	/**
	 * The accelo client id
	 */
	private static String clientID;

	/** 
	 * The accelo secret
	 */
	private static String secret; 
	
	
	private String accessToken = null;

	public enum HTTPMethod
	{
		GET, POST, PUT
	}

	static void setBaseURL(String baseURL)
	{
		AcceloApi.baseURL = baseURL + "/api/v0/";
	}
	
	static void setClientID(String clientID)
	{
		AcceloApi.clientID = clientID;
		
	}
	
	static void setClientSecret(String secret)
	{
		AcceloApi.secret = secret;
	}
	
	static public enum EndPoints
	{
		activities("activities"), affiliations("affiliations"), companies("companies"), contacts("contacts"), contracts(
				"contracts"), tickets("issues"), requests("requests"), staff("staff");

		private String endpoint;

		EndPoints(String endpoint)
		{
			this.endpoint = endpoint;
		}

		public String toString()
		{
			return this.endpoint;
		}

		public URL getURL() throws MalformedURLException
		{
			return new URL(baseURL + this.endpoint);
		}

		public URL getURL(int id) throws MalformedURLException
		{
			return new URL(baseURL + this.endpoint + "/" + id);
		}

		public URL getURL(String args) throws MalformedURLException
		{
			return new URL(baseURL + this.endpoint + "?" + args);
		}

		public URL getURL(int id, String path) throws MalformedURLException
		{
			return new URL(baseURL + this.endpoint + "/" + id + path);
		}

	};

	public <E> AcceloResponseList<E> getAll(EndPoints endPoint, Class<? extends AcceloResponseList<E>> clazz)
			throws AcceloException
	{
		AcceloResponseList<E> entity;

		if (accessToken == null)
			throw new AcceloException("Call connect() first");

		AcceloFieldList fieldList = new AcceloFieldList();

		// String args = "_fields=(_ALL)";

		fieldList.add("_All");

		try
		{
			URL url = new URL(baseURL + endPoint);

			entity = pull(HTTPMethod.GET, url, null, fieldList, clazz);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return entity;

	}

	/**
	 * E - the entity we returning but which is wrapped in R the
	 * AcceloResponse(List) object.
	 * 
	 * @param method
	 * @param url
	 * @param filterMap
	 * @param fieldList
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws AcceloException
	 */

	public <E, R> R pull(HTTPMethod method, URL url, AcceloFilter filterMap, AcceloFieldList fieldList, Class<R> clazz)
			throws IOException, AcceloException
	{
		String fields = fieldList.formatAsJson();
		String filters = (filterMap == null) ? null : filterMap.toJson();

		String json = buildJsonBody(method, fields, filters);

		HttpResponse response = _request(HTTPMethod.POST, url, json);

		return response.parseBody(clazz);

	}

	/*
	 * FieldValues is a map of name value pair that constitute the entity that
	 * we are pushing.
	 */
	public <T> T push(HTTPMethod method, URL url, AcceloFieldValues fieldNameValues, Class<T> clazz)
			throws IOException, AcceloException
	{
		// String json = buildJsonBody(method, fieldNameValues);
		
		// looks like you can't post fields via json so we need to add data to the url.
		
		String urlArgs = fieldNameValues.buildUrlArgs();
		
		URL completeUrl = new URL(url.toExternalForm() + "?" + urlArgs);
		HttpResponse response = _request(method, completeUrl, null);

		return response.parseBody(clazz);
	}

	/**
	 * Returns a raw response string.
	 * @throws AcceloException 
	 */
	public HttpResponse _request(HTTPMethod method, URL url, String jsonArgs) throws IOException, AcceloException
	{

		HttpResponse response;

		logger.error(method + " url: " + url.toString());
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		// We always use post as we are using json and defining the method via
		// _method in the json data.
		connection.setRequestMethod(method.toString());
		connection.setDoOutput(true);

		// connection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);

		connection.connect();


		// Write the json arguments if any exist.
		if (jsonArgs != null)
		{
			logger.error("jsonArgs: " + jsonArgs);
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
		
		// Read the resonse.
		try (InputStreamReader isr = ((responseCode < 300) ? new InputStreamReader(connection.getInputStream())
				: new InputStreamReader(connection.getErrorStream())))
		{
			int read;
			String buffer = new String();
			while ((read = isr.read()) != -1)
			{
				buffer += (char) read;
			}

			response = new HttpResponse(responseCode, connection.getResponseMessage(), buffer);
		}

		return response;

	}

	// retrieve the access token.
	public void connect()
	{
		try
		{

			// Enable to debug https connection
//			sun.util.logging.PlatformLogger.getLogger("sun.net.www.protocol.http.HttpURLConnection")
//					.setLevel(sun.util.logging.PlatformLogger.Level.ALL);

			if (AcceloApi.baseURL == null)
				throw new AssertionError("call AcceloApi.setBbaseURL first");
			if (AcceloApi.clientID == null)
				throw new AssertionError("call AcceloApi.setClientID first");
			if (AcceloApi.secret == null)
				throw new AssertionError("call AcceloApi.setSecret first");

			
			String baseURL = AcceloApi.baseURL + "/oauth2/v0/";
			

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
		catch (Exception e)
		{
			logger.debug("Exception during connect", e);
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

}
