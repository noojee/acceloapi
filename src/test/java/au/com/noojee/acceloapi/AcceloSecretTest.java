package au.com.noojee.acceloapi;

import java.io.FileNotFoundException;

import org.junit.Test;

public class AcceloSecretTest
{

	@Test
	public void test() throws FileNotFoundException
	{
		AcceloSecret secret = AcceloSecret.load();
		
		System.out.println(secret.getFQDN());
		System.out.println(secret.getClientId());
		System.out.println(secret.getClientSecret());
	}

}
