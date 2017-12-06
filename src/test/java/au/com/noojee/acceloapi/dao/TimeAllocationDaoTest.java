package au.com.noojee.acceloapi.dao;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.TimeAllocation;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class TimeAllocationDaoTest
{

	@Test
	public void test() throws FileNotFoundException
	{
			AcceloApi.getInstance().connect(AcceloSecret.load());

			Company company = new CompanyDao().getByName("Noojee Contact Solutions Pty Ltd");
			TicketDao daoTicket = new TicketDao();

			List<Ticket> tickets = daoTicket.getByCompany(company);

			Ticket ticketWithActivites = null;
			List<Activity> activities = null;

			// find a ticket with an activity
			for (Ticket ticket : tickets)
			{
				activities = new ActivityDao().getByTicket(ticket, true);
				if (!activities.isEmpty())
				{
					ticketWithActivites = ticket;
					break;
				}
			}
			if (ticketWithActivites != null)
			{
				Activity activity = activities.get(0);
				TimeAllocationDao daoTimeAllocation = new TimeAllocationDao();

				AcceloFilter<TimeAllocation> filter = new AcceloFilter<>();

				// filter.where(TimeAllocation_.activity_id, activity.getId());
				filter.where(filter.against(AgainstType.activities, activity.getId()));

				List<TimeAllocation> allocations = daoTimeAllocation.getByFilter(filter);
				
				
				/**
				 * 
				 * Looks like the response is a single object for all time logged against the activity and I don't think we can
				 * adjust it.
				 * 
				 * Might be able to delete the activity and recreate a replacement one with the new details.
				 * 
				 * https://noojee.api.accelo.com/api/v0/activities/allocations

"_filters": { "against":[{"activities": ["4445"]}]}

{"meta":{"message":"Everything executed as expected."
,"status":"ok","more_info":"https://api.accelo.com/docs/#status-codes"}
,"response":{"nonbillable":null,"billable":null,"charged":"0.00"}}

				 */

				for (TimeAllocation allocation : allocations)
				{
					System.out.println(allocation);
				}

			}

	}

}
