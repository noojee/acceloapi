package au.com.noojee.acceloapi;

import java.io.IOException;
import java.util.List;

import au.com.noojee.acceloapi.AcceloFilter.Search;

// import au.com.noojee.accelogateway.AcceloApi.EndPoints;

public class Contact
{
	public static final String FIELDS_ALL = "contact._ALL";
	private int id;
	private Company company;
	private String firstname;
	private String middlename;
	private String surname;
	private String preferred_name;
	private String username;
	private String password;
	private String title;
	private String salutation;
	private String comments;
	private int status;
	private String standing;
	private int country_id;
	private int physical_address_id;
	private int postal_address_id;
	private String phone;
	private String fax;
	private String email;
	private int status_id;
	private String communication;
	private String invoice_method;

	public static Contact getContact(AcceloApi acceloApi, String contact_firstname, String contact_lastname)
			throws AcceloException
	{
		Contact.Response response = null;
		try
		{
			if (contact_firstname != null && contact_lastname != null)
			{
				AcceloFilter filters = new AcceloFilter();
				filters.add(new Search(contact_firstname + " " + contact_lastname));

				response = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.contacts.getURL(), filters, AcceloFieldList.ALL, Contact.Response.class);
			}
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Contact contact = null;
		if (response != null)
			contact = response.getList().size() > 0 ? response.getList().get(0) : null;

		return contact;
	}

	public static List<Contact> getByPhone(AcceloApi acceloApi, String phone) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);
		fields.add(Company.FIELDS_ALL);
		
		AcceloFilter filters = new AcceloFilter();
		filters.add(new AcceloFilter.SimpleMatch("contact_number", phone));

		Contact.Response request;
		try
		{
			request = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.contacts.getURL(), filters, fields, Contact.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return request.getList();

	}

	public int getid()
	{
		return id;
	}

	public Company getCompany()
	{
		return company;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public String getMiddlename()
	{
		return middlename;
	}

	public String getSurname()
	{
		return surname;
	}

	public String getPreferred_name()
	{
		return preferred_name;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getTitle()
	{
		return title;
	}

	public String getSalutation()
	{
		return salutation;
	}

	public String getComments()
	{
		return comments;
	}

	public int getStatus()
	{
		return status;
	}

	public String getStanding()
	{
		return standing;
	}

	public int getCountry_id()
	{
		return country_id;
	}

	public int getPhysical_address_id()
	{
		return physical_address_id;
	}

	public int getPostal_address_id()
	{
		return postal_address_id;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getFax()
	{
		return fax;
	}

	public String getEmail()
	{
		return email;
	}

	public int getStatus_id()
	{
		return status_id;
	}

	public String getCommunication()
	{
		return communication;
	}

	public String getInvoice_method()
	{
		return invoice_method;
	}

	public class Response extends AcceloResponseList<Contact>
	{
	}

	@Override
	public String toString()
	{
		return "Contact [company=" + company + ", firstname=" + firstname + ", middlename=" + middlename
				+ ", surname=" + surname + ", preferred_name=" + preferred_name + ", username=" + username
				+ ", password=" + password + ", title=" + title + ", salutation=" + salutation + ", comments="
				+ comments + ", status=" + status + ", standing=" + standing + ", country_id=" + country_id
				+ ", physical_address_id=" + physical_address_id + ", postal_address_id=" + postal_address_id
				+ ", phone=" + phone + ", fax=" + fax + ", email=" + email + ", status_id=" + status_id
				+ ", communication=" + communication + ", invoice_method=" + invoice_method + "]";
	}

	public String getFullName()
	{
		return firstname + " " + surname;
	}

	public static List<Contact> getByCompany(AcceloApi api, Company company2)
	{
		return null;
	}

}
