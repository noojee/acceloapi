package au.com.noojee.acceloapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import com.google.gson.Gson;

/**
 * A convenience method to store you accelo secrets in a resource file (that you
 * keep out of git).
 * 
 * AcceloSecret secret = AcceloSecret.load();
 * 
 * The secrets file must be on your class path and must be called:
 * 
 * accelosecrets.json
 * 
 * The contents of the must be:
 * 
 * {"fqdn":"yourdomain.api.accelo.com", "client_id":"yourclientid",
 * "client_secret":"your client secret"}
 * 
 * 
 * @author bsutton
 *
 */
public class AcceloSecret
{

	private static final String ACCELOSECRETS_JSON = "secrets/accelosecrets.json";
	private String client_id;
	private String client_secret;
	private String fqdn;

	AcceloSecret(String FQDN, String clientId, String clientSecret)
	{
		this.fqdn = FQDN;
		this.client_id = clientId;
		this.client_secret = clientSecret;
	}

	public String getClientId()
	{
		return client_id;
	}

	public String getClientSecret()
	{
		return client_secret;
	}

	public String getFQDN()
	{
		return fqdn;
	}

	public static AcceloSecret load() throws FileNotFoundException
	{
		ClassLoader classLoader = AcceloSecret.class.getClassLoader();
		
		// The file must be located in the resource directory.
		URL resource = classLoader.getResource(ACCELOSECRETS_JSON);
		if (resource == null)
			throw new FileNotFoundException(ACCELOSECRETS_JSON);
		
		File file = new File(resource.getFile());
		
		AcceloSecret secret = load(file);

		return secret;
	}
	

	public static AcceloSecret load(File configFile) throws FileNotFoundException
	{
		FileReader fr = new FileReader(configFile);

		AcceloSecret secret = new Gson().fromJson(fr, AcceloSecret.class);

		return secret;
	}


}
