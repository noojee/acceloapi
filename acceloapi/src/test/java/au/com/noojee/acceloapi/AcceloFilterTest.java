package au.com.noojee.acceloapi;

import java.util.Date;

import org.junit.Test;

import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.After;
import au.com.noojee.acceloapi.filter.expressions.Compound;
import au.com.noojee.acceloapi.filter.expressions.Eq;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class AcceloFilterTest
{

	@Test
	public void test() throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		
		Compound against = filter.add(new Compound("against"));
		
		against = against.add(new Eq("company",  new String[] {"1", "2"}));
		
		against.add(new Eq("contract", "3"));
		
		against.add(new After("date_expried", new Date()));
		
		System.out.println(filter.toJson());
		
		filter = new AcceloFilter();
		filter.add(new Search("911"));
		
		System.out.println(filter.toJson());
		
		
		
	}

}
