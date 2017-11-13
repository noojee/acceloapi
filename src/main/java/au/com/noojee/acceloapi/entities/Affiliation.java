package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.DateFilterField;
import au.com.noojee.acceloapi.entities.generator.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.generator.SearchFilterField;
import au.com.noojee.acceloapi.util.Conversions;

public class Affiliation extends AcceloEntity<Affiliation>
{
	@BasicFilterField
	private int company; // id of the affiliated company
	
	@BasicFilterField
	private int postal_address; // id of an address
	
	@BasicFilterField
	private int physical_address; // id of an address
	
	
	@BasicFilterField
	private int contact; // contact id


	@SearchFilterField
	private String phone; // phone no.

	@SearchFilterField
	private String mobile; // mobile no.
	
	@SearchFilterField
	private String email; // email address
	
	private String position; // ??
	
	private long date_last_interacted; // unix time
	
	@DateFilterField
	private long date_modified; // unix time
	
	private int staff_bookmarked;
	
	@BasicFilterField
	private String standing;
	
	
	@BasicFilterField
	private String status;



	private String fax;
	
	
	/**
	 * You can search by these but they aren't returned when you query the entity.
	 * @author bsutton
	 *
	 */
	class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient String contact_number; 
		
		@SearchFilterField
		private transient String firstname;
		
		@SearchFilterField
		private transient String surname;
	}


	public int getCompanyId()
	{
		return company;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public int getPostalAddress()
	{
		return postal_address;
	}

	public void setPostal_address(int postal_address)
	{
		this.postal_address = postal_address;
	}

	public int getPhysicalAddress()
	{
		return physical_address;
	}

	public void setPhysicalAddress(int physical_address)
	{
		this.physical_address = physical_address;
	}

	public int getContactId()
	{
		return contact;
	}

	public void setContact(int contact)
	{
		this.contact = contact;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public LocalDate getDateLastInteracted()
	{
		return Conversions.toLocalDate(date_last_interacted);
	}

	public void setDateLastInteracted(LocalDate dateLastInteracted)
	{
		this.date_last_interacted = Conversions.toLong(dateLastInteracted);
	}

	public LocalDate getDateModified()
	{
		return Conversions.toLocalDate(date_modified);
	}

	public void setDateModified(LocalDate dateModified)
	{
		this.date_modified = Conversions.toLong(dateModified);
	}

	public int getStaffBookmarked()
	{
		return staff_bookmarked;
	}

	public void setStaffBookmarked(int staffBookmarked)
	{
		this.staff_bookmarked = staffBookmarked;
	}

	public String getStanding()
	{
		return standing;
	}

	public void setStanding(String standing)
	{
		this.standing = standing;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPosition()
	{
		return position;
	}
	
	public String getFax()
	{
		return fax;
	}
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	@Override
	public int compareTo(Affiliation o)
	{
		return this.getDateLastInteracted().compareTo(o.getDateLastInteracted());
	}

	
	@Override
	public String toString()
	{
		return "Affiliation [id=" + getId() + ", company=" + company + ", postal_address=" + postal_address
				+ ", physical_address=" + physical_address + ", phone=" + phone + ", contact=" + contact + ", mobile="
				+ mobile + ", email=" + email + ", position=" + position + ", date_last_interacted="
				+ date_last_interacted + ", date_modified=" + date_modified + ", staff_bookmarked=" + staff_bookmarked
				+ ", standing=" + standing + "]";
	}




}
