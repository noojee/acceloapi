package au.com.noojee.acceloapi;

import org.junit.Test;

import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.meta.Company_;
import au.com.noojee.acceloapi.entities.meta.Ticket_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class MetaFieldDataTest
{

	@Test
	public void test()
	{
		AcceloFilter<Ticket> tf = new AcceloFilter<>();
		tf.where(tf.eq(Ticket_.id, 1).or(tf.eq(Ticket_.id, 2)));
		
		System.out.println(tf.toJson());
		
		
		AcceloFilter<Company> cf = new AcceloFilter<>();
		cf.where(cf.eq(Company_.id, 1).or(cf.eq(Company_.id, 2)));
		
		System.out.println(cf.toJson());

	}

}
