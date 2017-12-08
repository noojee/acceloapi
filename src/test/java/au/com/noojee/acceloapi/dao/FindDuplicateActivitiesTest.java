package au.com.noojee.acceloapi.dao;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.meta.Activity_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class FindDuplicateActivitiesTest
{

	@Test
	public void test() throws FileNotFoundException
	{
		
		AcceloSecret secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);
	
		
		ActivityDao daoActivity= new ActivityDao();
		
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.limit(10000);
		
		
		filter.where(filter.after(Activity_.date_created, LocalDateTime.of(2017, 12,7,17,20)).and(filter.eq(Activity_.against_type, AgainstType.issue)));
		
		List<Activity> activities = daoActivity.getByFilter(filter);
		
		activities = activities.stream().sorted((a,b)-> a.getAgainstId() - b.getAgainstId() ).collect(Collectors.toList());
		
		for (Activity activity : activities)
		{
			System.out.println("Against:" + activity.getAgainstType() + " id:" + activity.getAgainstId() + " Billable: " + activity.getBillable().getSeconds() +  " Activity: " + activity.getId() + " Description: " +  activity.getSubject());
		}
		System.out.println("Count:" + activities.size());
	}
}