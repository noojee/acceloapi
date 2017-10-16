package au.com.noojee.acceloapi;

import java.time.LocalDate;

import org.junit.Test;

import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.After;
import au.com.noojee.acceloapi.filter.expressions.Eq;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class AcceloFilterTest
{

	@Test
	public void test() throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();

		// Compound against = filter.set(new Compound("against"));

		filter.where(new Eq("against_type", "company"));

		System.out.println(filter.toJson());

		filter.where(new Eq("against_type", "company").and(new Eq("company", "1")));

		System.out.println(filter.toJson());

		filter.where(new Eq("company", "1").or(new Eq("company", "2")));

		System.out.println(filter.toJson());

		filter.where(new Eq("against_type", "company")
				.and(new Eq("company", "1").or(new Eq("company", "2")))
				);

		System.out.println(filter.toJson());

		filter.where(new Eq("against_type", "company")
				.or(new Eq("company", "1").and(new Eq("contract", "3")))
				);

		System.out.println(filter.toJson());

		filter.where(new Eq("against_type", "company")
				.and(new Eq("company", "1").or(new Eq("company", "2")))
				.and(new Eq("contract", "3"))
				);

		System.out.println(filter.toJson());

		filter.where(new Eq("against_type", "company")
				.and(new Eq("company", "1").or(new Eq("company", "2")))
				.and(new Eq("contract", "3"))
				.and(new After("date_expried", LocalDate.now()))
				);

		System.out.println(filter.toJson());
		
		filter.where(new Eq("against_type", "company")
				.and(new Eq("company", "1").or(new Eq("company", "2")))
				.and(new Eq("contract", "3"))
				.and(new After("date_expried", LocalDate.now()))
				);

System.out.println(filter.toJson());


		filter = new AcceloFilter();
		filter.where(new Search("911"));

		System.out.println(filter.toJson());

	}

}
