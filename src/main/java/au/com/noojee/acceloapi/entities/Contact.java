package au.com.noojee.acceloapi.entities;

import java.time.LocalDateTime;

import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.InsertOnlyField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.SearchFilterField;

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
	private int contact_status;
	@BasicFilterField
	private String standing;
	private int country_id;
	private int physical_address_id;
	private int postal_address_id;
	private String phone;
	private String mobile;
	private String fax;
	@BasicFilterField
	@SearchFilterField
	private String email;
	private int default_affiliation; // id of the contacts affiliation. Will normally be the company they are primarily
										// associated with.
	private String communication;
	private String invoice_method;

	@DateFilterField
	private LocalDateTime date_created ;
	@DateFilterField
	private LocalDateTime date_modified ;
	
	
	@InsertOnlyField
	private int company_id; // only used when inserting new contacts. This value is not normally available.

	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient String affiliation; // matches on the default_affiliation.

		@BasicFilterField
		private transient String contact_number; // filters over phone, fax and mobile

	}

	public String getFirstname()
	{
		return firstname;
	}
	
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	public String getMiddlename()
	{
		return middlename;
	}

	public String getSurname()
	{
		return surname;
	}
	
	public void setSurname(String surname)
	{
		this.surname = surname;
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
	
	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public int getStatus()
	{
		return contact_status;
	}

	public String getStanding()
	{
		return standing;
	}

	public int getCountryId()
	{
		return country_id;
	}

	public int getPhysicalAddressId()
	{
		return physical_address_id;
	}

	public int getPostalAddressId()
	{
		return postal_address_id;
	}

	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getMobile()
	{
		return mobile;
	}
	
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getFax()
	{
		return fax;
	}

	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
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
	
	public void setDefaultAffiliation(int affiliationId)
	{
		this.default_affiliation = affiliationId;
	}
	
	/**
	 * You can only set this if you are inserting a new contact.
	 * At all other times the value is meaningless.
	 * 
	 * @param company_id
	 */
	public void setCompanyId(Integer company_id)
	{
		this.company_id = (company_id == null ? 0 : company_id);
	}

	@Override
	public String toString()
	{
		return "Contact [id=" + getId() + ", firstname=" + firstname + ", middlename=" + middlename
				+ ", surname=" + surname + ", preferred_name=" + preferred_name + ", username=" + username
				+ ", password=" + password + ", title=" + title + ", salutation=" + salutation + ", comments="
				+ comments + ", contact_status=" + contact_status + ", standing=" + standing + ", country_id=" + country_id
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
