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
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.DateFilterField;
import au.com.noojee.acceloapi.entities.generator.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.generator.SearchFilterField;
import au.com.noojee.acceloapi.util.Conversions;

public class Company extends AcceloEntity<Company>
{
	public static final String FIELDS_ALL = "company._ALL";
	
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

	private long date_last_interacted;
	
	private int custom_id;
	
	private String staff_bookmarked;
	
	private transient List<CustomField> customFields = new ArrayList<>();
	

	
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

			String path = "/" + this.getId() + "/profiles/values";

			response = AcceloApi.getInstance().get(new URL(EndPoint.companies.getURL(), path), null, AcceloFieldList.ALL,
					CustomFieldsResponse.class, 0);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		this.customFields = response.getList();
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
		return Conversions.toLocalDate(date_created);
	}

	public LocalDate getDateModified()
	{
		return Conversions.toLocalDate(date_modified);
	}

	public String getComments()
	{
		return comments;
	}


	@Override
	public String toString()
	{
		return "Company [id=" + getId() + ", name=" + name + ", website=" + website + ", standing=" + standing + ", status="
				+ status + ", phone=" + phone + ", fax=" + fax + ", date_created=" + date_created + ", date_modified="
				+ date_modified + ", comments=" + comments + "]";
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

	public long getDate_last_interacted()
	{
		return date_last_interacted;
	}


	public void setDate_last_interacted(long date_last_interacted)
	{
		this.date_last_interacted = date_last_interacted;
	}


	public int getCustom_id()
	{
		return custom_id;
	}


	public void setCustom_id(int custom_id)
	{
		this.custom_id = custom_id;
	}


	public String getStaff_bookmarked()
	{
		return staff_bookmarked;
	}


	public void setStaff_bookmarked(String staff_bookmarked)
	{
		this.staff_bookmarked = staff_bookmarked;
	}


	@Override
	public int compareTo(Company o)
	{
		return this.getName().compareTo(o.getName());
	}

}
