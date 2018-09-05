package au.com.noojee.acceloapi.dao;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.meta.Activity_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ActivityDaoTest
{

	@Test
	public void test()
	{

		AcceloSecret secret;
		try
		{
			secret = AcceloSecret.load();
			AcceloApi.getInstance().connect(secret);

			ActivityDao daoActivity = new ActivityDao();
			Activity activity = daoActivity.getById(349599);

			System.out.println(activity);

			List<Activity> list = null;
			AcceloFilter<Activity> filter = new AcceloFilter<>();

			LocalDate firstDate = LocalDate.of(2018, 8, 1);


			//filter.where(filter.afterOrEq(Activity_.date_created, firstDate.atStartOfDay()));

			filter.where(filter.eq(Activity_.against_type, AgainstType.issue))
					.and(filter.afterOrEq(Activity_.date_created, firstDate.atStartOfDay()))
					 // If the staff field is 0 then this is a system generated activity so lets exclude it.
					.and(filter.greaterThan(Activity_.staff, 0));

		
			filter.noLimit();
			list = new ActivityDao().getByFilter(filter);
			
			System.out.println("count: " + list.size());
			
			for (Activity item : list)
			{
				if (item.getId() == 349599)
					System.out.println("found it");
			}

		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail("Excepton");
		}

	}

}

