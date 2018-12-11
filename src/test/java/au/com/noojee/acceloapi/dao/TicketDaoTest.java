package au.com.noojee.acceloapi.dao;

import java.io.FileNotFoundException;
import java.time.Duration;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Staff;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.types.AgainstType;

public class TicketDaoTest
{

	@Test
	public void test() throws FileNotFoundException
	{
		AcceloSecret secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);
		
		TicketDao daoTicket = new TicketDao();
		StaffDao daoStaff = new StaffDao();
		
		Staff staff = daoStaff.getByEmail("bsutton@noojee.com.au");
		
		CompanyDao daoCompany = new CompanyDao();
		
		Company company = daoCompany.getByName("Noojee Contact Solutions Pty Ltd");
		
		Ticket ticket = new Ticket();
		
		ticket.setIssueType(2); // general support
		ticket.setAgainst(AgainstType.company, company.getId());
		ticket.setDescription("Description:  test ticket via the api for TicketDaoTest");
		ticket.setTitle("A test ticket via the api for TicketDaoTest");
		ticket.setResolvedBy(staff.getId());
		ticket.setOpenedBy(staff.getId());
		ticket.setEngineerAssigned(staff.getId());
		ticket = daoTicket.insert(ticket);
		
		ActivityDao daoActivity = new ActivityDao();
		Activity activity = new Activity();
		activity.setSubject("A test activity with 15 minutes of labour - approved.");
		activity.setAgainst(AgainstType.ticket, ticket.getId());
		activity.setBillable(Duration.ofMinutes(15));
		activity.setOwner(ActivityOwnerType.staff, staff.getId());
		daoActivity.insert(activity);
		
		
		
		ticket.setIssueType(15); // SPAM
		
		ticket = daoTicket.update(ticket);
		
		System.out.println(ticket);
		
		
		
		daoTicket.delete(ticket);
	}

}
