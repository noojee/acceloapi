package au.com.noojee.acceloapi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.AcceloFilter.Search;

public class Company
{

	public class Response extends AcceloResponseList<Company>
	{

	}

	public static final String FIELDS_ALL = "company._ALL";

	private int id;
	private String name;
	private String website;
	private String standing;
	private int status; // can be an object
	private String phone;
	private String fax;
	private int date_created;
	private int date_modified;
	private String comments;
	private int default_affiliation;
	private Contact contact;
	private List<CustomField> customFields = new ArrayList<>();

	public static Company getByName(AcceloApi api, String companyName) throws AcceloException
	{
		Company.Response response;
		try
		{
			//String args = "_search=" + URLEncoder.encode(companyName, "UTF-8");
					//+ "&_fields=(_ALL),contact(_ALL)";
			AcceloFieldList fields = new AcceloFieldList();
			fields.add(AcceloFieldList._ALL);
			fields.add(Contact.FIELDS_ALL);
			
			AcceloFilter filter = new AcceloFilter();
			filter.add(new Search(companyName));

			response = api.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.companies.getURL(), filter, fields, Company.Response.class);
	    	if (!response.isOK())
				throw new AcceloException(response.getStatusMessage());
	    	
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Company company = null;
		company = response.getList().size() > 0 ? response.getList().get(0) : null;

		return company;

	}

	public static Company getById(AcceloApi api, String companyId) throws AcceloException
	{
		Company.Response response;
		try
		{

			AcceloFilter filters = new AcceloFilter();
			filters.add(new AcceloFilter.SimpleMatch("id", companyId));

			response = api.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.companies.getURL(), filters, AcceloFieldList.ALL, Company.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Company company = null;
		company = response.getList().size() > 0 ? response.getList().get(0) : null;

		return company;
	}

	static public class CustomFieldsResponse extends AcceloResponseList<CustomField>
	{
	}

	public void retrieveCustomFields(AcceloApi api) throws AcceloException
	{

		CustomFieldsResponse response;
		try
		{

			String path = "/" + this.id + "/profiles/values";

			response = api.pull(AcceloApi.HTTPMethod.GET, new URL(AcceloApi.EndPoints.companies.getURL(), path), null, AcceloFieldList.ALL, CustomFieldsResponse.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		this.customFields = response.getList();

		// return company;

	}

	public static Company getCompanyByContactId(AcceloApi api, String contractId) throws AcceloException
	{
		Company.Response response;
		try
		{
		
			AcceloFilter filters = new AcceloFilter();
			filters.add(new AcceloFilter.SimpleMatch("id",  contractId));
			

			response = api.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.companies.getURL(), filters, AcceloFieldList.ALL, Company.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Company company = null;
		company = response.getList().size() > 0 ? response.getList().get(0) : null;

		return company;
	}

	public static List<Company> getList(AcceloApi acceloApi) throws AcceloException
	{
		Company.Response request;
		try
		{
			request = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.companies.getURL(), null, AcceloFieldList.ALL, Company.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return request.getList();

	}

	public int getId()
	{
		return id;
	}

	public void setId(int companyId)
	{
	    this.id = companyId;
	}

	public String getName()
	{
		return name;
	}

	public String getWebsite()
	{
		return website;
	}

	public String getStanding()
	{
		return standing;
	}

	public int getStatus()
	{
		return status;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getFax()
	{
		return fax;
	}

	public int getDate_created()
	{
		return date_created;
	}

	public int getDate_modified()
	{
		return date_modified;
	}

	public String getComments()
	{
		return comments;
	}

	public Contact getContact()
	{
		return contact;
	}

	@Override
	public String toString()
	{
		return "Company [id=" + id + ", name=" + name + ", website=" + website + ", standing=" + standing + ", status="
				+ status + ", phone=" + phone + ", fax=" + fax + ", date_created=" + date_created + ", date_modified="
				+ date_modified + ", comments=" + comments + ", contact=" + contact + "]";
	}

	public int getDefault_affiliation()
	{
		return default_affiliation;
	}

	public void setDefault_affiliation(int default_affiliation)
	{
		this.default_affiliation = default_affiliation;
	}

	public String getPIN()
	{
		String PIN = "";
		for (CustomField field : this.customFields)
		{
			if (field.field_name.compareToIgnoreCase("Contract PIN") == 0)
			{
				PIN = field.value;
				break;
			}
		}
		return PIN;
	}

}
