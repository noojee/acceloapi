package au.com.noojee.acceloapi.dao.gson;

import org.junit.Test;

import au.com.noojee.acceloapi.entities.Ticket;

public class GsonForAcceloTest
{

	@Test
	public void test()
	{
		String json = "{\"status\":" +
				 "{\"start\":\"no\",\"color\":\"grey\",\"standing\":\"closed\",\"title\":\"Closed\",\"id\":\"4\",\"ordering\":\"0\"}\n" + 
				"\n" + 
				",\"type\":\"2\",\"affiliation\":\"7237\",\"date_closed\":\"1512219936\",\"date_started\":\"1511960400\"\n" + 
				",\"referrer_id\":null,\"resolution\":\"15\",\"against_type\":\"company\",\"referrer_type\":null,\"staff_bookmarked\":\"0\"\n" + 
				",\"billable_seconds\":\"1800\",\"date_due\":null,\"resolved_by\":\"1\",\"class\":\"1\",\"issue_object_budget\":\"14002\"\n" + 
				",\"date_opened\":\"1512003115\",\"date_last_interacted\":\"1512354118\",\"date_submitted\":\"1512003115\",\"assignee\":\"2\"\n" + 
				",\"company\":\"3617\",\"object_budget\":\"14002\",\"date_resolved\":\"1512219936\",\"contract\":\"33\",\"standing\":\"closed\"\n" + 
				",\"description\":\"Recieved email\",\"closed_by\":\"1\",\"custom_id\":null\n" + 
				",\"resolution_detail\":\"Can you please \",\"submitted_by\":\"2\",\"contact\":\"7237\",\"priority\":\"2\",\"opened_by\":\"2\"\n" + 
				"}";
		
		Ticket ticket = GsonForAccelo.fromJson(json, Ticket.class);
		
		System.out.println(ticket);
	}

}
