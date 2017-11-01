package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.CustomField;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.meta.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.meta.SearchFilterField;

public class Company extends AcceloEntity<Company>
{
	public static final String FIELDS_ALL = "company._ALL";

	@BasicFilterField
	private int id;
	
	@SearchFilterField
	private String name;
	
	@SearchFilterField
	private String website;
	@BasicFilterField
	private String standing;
	
	@BasicFilterField
	private int status; 
	
	@SearchFilterField
	private String phone;
	
	@SearchFilterField
	private String fax;
	@BasicFilterField
	private int postal_address;
	
	@DateFilterField
	private int date_created;
	@DateFilterField
	private int date_modified;
	private String comments;
	@BasicFilterField
	private int default_affiliation;
	private Contact contact;
	private List<CustomField> customFields = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient int manager_id;
		
		@BasicFilterField
		private transient String contact_number;  // filters over phone and fax

	}

	static public class CustomFieldsResponse extends AcceloResponseList<CustomField>
	{
	}

	public void retrieveCustomFields() throws AcceloException
	{

		CustomFieldsResponse response;
		try
		{

			String path = "/" + this.id + "/profiles/values";

			response = AcceloApi.getInstance().get(new URL(EndPoint.companies.getURL(), path), null, AcceloFieldList.ALL,
					CustomFieldsResponse.class, 0);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		this.customFields = response.getList();

		// return company;

	}

	@Override
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

	public LocalDate getDateCreated()
	{
		return toLocalDate(date_created);
	}

	public LocalDate getDateModified()
	{
		return toLocalDate(date_modified);
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
			if (field.getName().compareToIgnoreCase("Contract PIN") == 0)
			{
				PIN = field.getValue();
				break;
			}
		}
		return PIN;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Company o)
	{
		return this.getName().compareTo(o.getName());
	}

}
