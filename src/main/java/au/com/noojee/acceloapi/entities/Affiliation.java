package au.com.noojee.acceloapi.entities;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;

import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.SearchFilterField;

public class Affiliation extends AcceloEntity<Affiliation>
{
	@BasicFilterField
	@SerializedName(value="company_id", alternate="company")
	private int company; // id of the affiliated company
	
	@BasicFilterField
	@SerializedName(value="contact_id", alternate="contact")
	private int contact; // contact id


	@SerializedName(value="country_d", alternate="country")
	private int country; // id of country

	@BasicFilterField
	@SerializedName(value="postal_address_id", alternate="postal_address")
	private int postal_address; // id of an address

	@BasicFilterField
	@SerializedName(value="physical_address_id", alternate="physical_address")
	private int physical_address; // id of an address

	
	@SearchFilterField
	private String phone; // phone no.

	@SearchFilterField
	private String mobile; // mobile no.

	@SearchFilterField
	private String email; // email address

	private String position; // ??

	private LocalDateTime date_last_interacted; // unix time

	@DateFilterField
	private LocalDateTime date_modified; // unix time

	private int staff_bookmarked;

	@BasicFilterField
	private String standing;

	@BasicFilterField
	@SerializedName(value="status_id", alternate="status")
	private String status;

	private String fax;

	/**
	 * You can search by these but they aren't returned when you query the entity.
	 * 
	 * @author bsutton
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
	
	public void setCompany(int company)
	{
		this.company = company;
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

	public LocalDateTime getDateTimeLastInteracted()
	{
		return date_last_interacted;
	}

	public void setDateTimeLastInteracted(LocalDateTime dateLastInteracted)
	{
		this.date_last_interacted = dateLastInteracted;
	}

	public LocalDateTime getDateTimeModified()
	{
		return date_modified;
	}

	public void setDateTimeModified(LocalDateTime dateModified)
	{
		this.date_modified = dateModified;
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
		return this.getDateTimeLastInteracted().compareTo(o.getDateTimeLastInteracted());
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
