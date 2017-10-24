package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;

public class Affiliation extends AcceloEntity<Affiliation>
{
	private int id;

	private String company;
	private int postal_address; // id of an address
	private int physical_address; // id of an address
	private String phone; // phone no.
	private int contact; // contact id
	private String mobile; // mobile no.
	private String email; // email address
	private String position; // ??
	private long date_last_interacted; // unix time
	private long date_modified; // unix time
	private int staff_bookmarked;
	private String standing;


	@Override
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "Affiliation [id=" + id + ", company=" + company + ", postal_address=" + postal_address
				+ ", physical_address=" + physical_address + ", phone=" + phone + ", contact=" + contact + ", mobile="
				+ mobile + ", email=" + email + ", position=" + position + ", date_last_interacted="
				+ date_last_interacted + ", date_modified=" + date_modified + ", staff_bookmarked=" + staff_bookmarked
				+ ", standing=" + standing + "]";
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

	public String getCompany()
	{
		return company;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public int getPostal_address()
	{
		return postal_address;
	}

	public void setPostal_address(int postal_address)
	{
		this.postal_address = postal_address;
	}

	public int getPhysical_address()
	{
		return physical_address;
	}

	public void setPhysical_address(int physical_address)
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
		return toLocalDate(date_last_interacted);
	}

	public void setDateLastInteracted(LocalDate dateLastInteracted)
	{
		this.date_last_interacted = toDateAsLong(dateLastInteracted);
	}

	public LocalDate getDateModified()
	{
		return toLocalDate(date_modified);
	}

	public void setDateModified(LocalDate dateModified)
	{
		this.date_modified = toDateAsLong(dateModified);
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
	
	@Override
	public int compareTo(Affiliation o)
	{
		return this.getDateLastInteracted().compareTo(o.getDateLastInteracted());
	}


}
