package au.com.noojee.acceloapi;

import java.time.LocalDate;

import org.junit.Test;

import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.meta.Company_;
import au.com.noojee.acceloapi.entities.meta.Contract_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class AcceloFilterTest
{

	@Test
	public void test() throws AcceloException
	{
		AcceloFilter<Company> filter = new AcceloFilter<>();

		filter.where(filter.against(AgainstType.company, 1));

		System.out.println(filter.toJson());

		filter.where(filter.against(AgainstType.company, 1));

		System.out.println(filter.toJson());

		filter.where(filter.eq(Company_.id, 1).or(filter.eq(Company_.id, 2)));

		System.out.println(filter.toJson());

		filter.where(filter.against(AgainstType.company, 1, 2));

		System.out.println(filter.toJson());

		AcceloFilter<Contract> cf = new AcceloFilter<>();

		cf.where(cf.against(AgainstType.company, 1).and(cf.eq(Contract_.id, 3)));

		System.out.println(cf.toJson());

		filter.where(cf.against(AgainstType.company, 1, 2).and(cf.eq(Contract_.id, 3)));

		System.out.println(cf.toJson());

		filter.where(cf.against(AgainstType.company, 1, 2)
				.and(cf.eq(Contract_.id, 3))
				.and(cf.after(Contract_.date_expires, LocalDate.now())));

		System.out.println(filter.toJson());

		filter.where(cf.against(AgainstType.company, 1, 2)
				.and(cf.eq(Contract_.id, 3))
				.and(cf.after(Contract_.date_expires, LocalDate.now())));

		System.out.println(cf.toJson());

		cf = new AcceloFilter<>();
		filter.where(cf.search("911"));

		System.out.println(filter.toJson());

	}

}
