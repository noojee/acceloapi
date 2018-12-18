package au.com.noojee.acceloapi.dao;

import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Priority;

public class PriorityDaoTest
{

	@Test
	public void test() throws Throwable
	{
		AcceloSecret secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);
		
		PriorityDao daoPriority = new PriorityDao();
		
		List<Priority> priorities= daoPriority.getAll();
		
		priorities.stream().forEach(p -> System.out.println(p.toString()));
		

	}

}
