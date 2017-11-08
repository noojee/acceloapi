package au.com.noojee.acceloapi.entities;

import au.com.noojee.acceloapi.entities.meta.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.meta.SearchFilterField;

public class Contact extends AcceloEntity<Contact>
{
	public static final String FIELDS_ALL = "contact._ALL";

	@SearchFilterField
	private String firstname;
	private String middlename;
	@SearchFilterField
	private String surname;
	private String preferred_name;
	@BasicFilterField
	private String username;
	private String password;
	@BasicFilterField
	private String title;
	private String salutation;
	private String comments;
	@BasicFilterField
	private int status;
	@BasicFilterField
	private String standing;
	private int country_id;
	private int physical_address_id;
	private int postal_address_id;
	private String phone;
	private String fax;
	@BasicFilterField
	@SearchFilterField
	private String email;
	private int default_affiliation; // id of the contacts affiliation. Will normally be the company they are primarily associated with.
	private String communication;
	private String invoice_method;
	
	@DateFilterField
	private int date_created;
	@DateFilterField
	private int date_modified;

	
	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient String affiliation; // matches on the default_affiliation.
		
		@BasicFilterField
		private transient String contact_number;  // filters over phone, fax and mobile

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

	public String getCommunication()
	{
		return communication;
	}

	public String getInvoice_method()
	{
		return invoice_method;
	}
	
	public int getDefaultAffiliation()
	{
		return default_affiliation;
	}



	@Override
	public String toString()
	{
		return "Contact [id=" + getId() +  ", firstname=" + firstname + ", middlename=" + middlename
				+ ", surname=" + surname + ", preferred_name=" + preferred_name + ", username=" + username
				+ ", password=" + password + ", title=" + title + ", salutation=" + salutation + ", comments="
				+ comments + ", status=" + status + ", standing=" + standing + ", country_id=" + country_id
				+ ", physical_address_id=" + physical_address_id + ", postal_address_id=" + postal_address_id
				+ ", phone=" + phone + ", fax=" + fax + ", email=" + email 
				+ ", communication=" + communication + ", invoice_method=" + invoice_method + "]";
	}

	public String getFullName()
	{
		return firstname + " " + surname;
	}

	
	
	@Override
	public int compareTo(Contact o)
	{
		return this.getSurname().compareTo(o.getSurname());
	}




	
}
