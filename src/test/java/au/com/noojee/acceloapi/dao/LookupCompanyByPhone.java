package au.com.noojee.acceloapi.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Affiliation;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;

public class LookupCompanyByPhone
{

	@Test
	public void test()
	{
		AcceloSecret secret;
		try
		{
			secret = AcceloSecret.load();
			AcceloApi.getInstance().connect(secret);

			ContactDao daoContact = new ContactDao();
			List<Contact> contacts = daoContact.getByPhone("0385089777");

			for (Contact contact : contacts)
			{
				System.out.println(contact);
			}
			if (contacts != null && contacts.size() > 0)
			{
				Contact contact = contacts.get(0);
				int affiliationId = contact.getDefaultAffiliation();
				print("Affiliation: " + affiliationId);

				Affiliation affiliation = new AffiliationDao().getById(affiliationId);
				int companyId = affiliation.getCompanyId();
				print(Integer.toString(companyId));
				Company company = new CompanyDao().getById(companyId);

				if (company != null)
					print(company.toString());
				else
					print("no company found");
			}

		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail("Excepton");
		}

	}

	void print(String text)
	{
		System.out.println(text);
	}

}
