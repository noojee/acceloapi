package au.com.noojee.acceloapi.dao;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Ticket;

public class TicketRoundingTest
{

	@Test
	public void test()
	{
		int rounded = 15;
		int activity = 5;
		// Duration totalBillable = 7;
		//
		// Duration roundedBillable = Duration.ofMinutes(rounded).minus(totalBillable)
		// .plus(selectedActivity.getBillable());
		//
		// System.out.println(arg0);

	}
	
	@Test
	public void ticketTest() throws FileNotFoundException
	{
		AcceloSecret secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);
	
		
		TicketDao daoTicket = new TicketDao();
		
		Ticket ticket = daoTicket.getById(17852);
		
		daoTicket.roundBilling(ticket, 15, 5);
	}

	// @Test
	public void activityTest()
	{

		long roundToMinutes = 15;
		long leawayMinutes = 5;

		List<Activity> activities = new ArrayList<>();

		Activity activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 23, 17, 31));
		activity.setBillable(Duration.ofMinutes(1));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 10, 46));
		activity.setBillable(Duration.ofMinutes(3));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 11, 0));
		activity.setBillable(Duration.ofMinutes(1));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 14, 16));
		activity.setBillable(Duration.ofMinutes(1));
		activities.add(activity);

		activity = new Activity();
		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 14, 45));
		activity.setBillable(Duration.ofMinutes(15));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 9, 43));
		activity.setBillable(Duration.ofMinutes(0));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 9, 43));
		activity.setBillable(Duration.ofMinutes(1));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 9, 44));
		activity.setBillable(Duration.ofMinutes(1));
		activities.add(activity);

		activity = new Activity();
		activity.setDateTimeStarted(LocalDateTime.of(2017, 11, 24, 10, 21));
		activity.setBillable(Duration.ofMinutes(112)); // 1:52
		activities.add(activity);

		List<Activity> sortedActivities = activities.stream()
				.sorted((a, b) -> (a.getDateTimeStarted().isAfter(b.getDateTimeStarted()) ? 1 : -1)).collect(Collectors.toList());
		
		for (Activity sortedActivity : sortedActivities)
			System.out.println(sortedActivity);
			

		// Find the oldest activity
		Optional<Activity> oldestActivity = activities.stream()
				.sorted((a, b) -> (a.getDateTimeStarted().isAfter(b.getDateTimeStarted()) ? 1 : -1)).findFirst();

		// calculate the current total billable for all activities against this ticket.
		Duration totalBillable = activities.parallelStream().map(Activity::getBillable).reduce(Duration.ZERO,
				(lhs, rhs) -> lhs.plus(rhs));

		long minutes =totalBillable.toMinutes();
		long rounded = calcRoundingData(totalBillable, roundToMinutes, leawayMinutes);
		
		System.out.println("Total Minutes: " + minutes + " Rounded Minutes:" + rounded);

		// If billable minutes is zero then we assume this ticket isn't billable.
		if (minutes != 0 && rounded != minutes)
		{
			// Update the selected activity so the total billable for the ticket is rounded up to the nearest
			// fifteen minutes.
			Duration roundedBillable= Duration.ofMinutes(rounded).minusMinutes(minutes).plus(oldestActivity.get().getBillable());

			System.out.println("Rounding value:" + roundedBillable);
		}
		else
			System.out.println("No rounding: ");

	}

	private long calcRoundingData(Duration totalBillable, long roundToMinutes, long leawayMinutes)
	{

		long minutes = totalBillable.toMinutes();
		long rounded = minutes;

		// We only round up if they are more than 5 minutes into the next block.
		long excess = minutes % roundToMinutes;
		if (minutes < roundToMinutes || excess > leawayMinutes)
			rounded = (long) (Math.ceil(minutes / (float) roundToMinutes) * roundToMinutes);

		return rounded;
	}

}
