package au.com.noojee.acceloapi;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFilter;
import au.com.noojee.acceloapi.AcceloFilter.CompoundMatch;

public class AcceloFilterTest
{

	@Test
	public void test() throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		
		CompoundMatch against = filter.add(new AcceloFilter.CompoundMatch("against"));
		
		against = against.add(new AcceloFilter.SimpleMatch("company",  new String[] {"1", "2"}));
		
		against.add(new AcceloFilter.SimpleMatch("contract", "3"));
		
		System.out.println(filter.toJson());
		
		filter = new AcceloFilter();
		filter.add(new AcceloFilter.Search("911"));
		
		System.out.println(filter.toJson());
		
		
		
	}

}
