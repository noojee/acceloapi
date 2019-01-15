
package au.com.noojee.acceloapi.dao;

import java.io.FileNotFoundException;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Affiliation;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.types.AgainstType;

public class TicketAffiliationTest
{

	@Test
	public void test() throws FileNotFoundException
	{
		AcceloSecret secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);

		
		AffiliationDao daoAffiliation = new AffiliationDao();
		
		Affiliation affiliation = daoAffiliation.getById(3308);
		System.out.println(affiliation);
		
		affiliation = daoAffiliation.getById(8801);
		
		System.out.println(affiliation);
		
		ContactDao daoContact = new ContactDao();
		
		Contact contact = daoContact.getById(affiliation.getContactId());
		
		System.out.println(contact);
		
		CompanyDao daoCompany = new CompanyDao();
		
		Company company = daoCompany.getById(affiliation.getCompanyId());
		
		System.out.println(company);
		
		
		Ticket ticket = new Ticket();
		ticket.setAgainst(AgainstType.company, company.getId());
		ticket.setAffiliation(affiliation.getId());
		ticket.setTitle("A title");
		ticket.setDescription("test ticket");
		
		System.out.println("Inserting: " + ticket);
		
		
		TicketDao daoTicket = new TicketDao();
		ticket = daoTicket.insert(ticket);
		System.out.println(ticket);
		
		

	}

}
