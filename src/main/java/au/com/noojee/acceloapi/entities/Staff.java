package au.com.noojee.acceloapi.entities;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.MetaBasicFilterFields;

public class Staff extends AcceloEntity<Staff>
{
	
	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient String standing;  

	}

	private String firstname;
	private String surname;
	@BasicFilterField
	private String username;
	@BasicFilterField
	private String email;
	private String phone;
	private String mobile;
	private String title;
	private String timezone;
	private String position;
	private String access_level;
	private String financial_level;
	private String standing;
	private String fax;
	

	public String getSurname()
	{
		return surname;
	}

	public String getUsername()
	{
		return username;
	}

	public String getEmail()
	{
		return email;
	}

	public String getMobile()
	{
		return mobile;
	}

	public String getFullName()
	{
		return firstname + " " + surname;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getTitle()
	{
		return title;
	}

	public String getTimezone()
	{
		return timezone;
	}

	public String getPosition()
	{
		return position;
	}

	public String getAccess_level()
	{
		return access_level;
	}

	public String getFinancial_level()
	{
		return financial_level;
	}

	public String getStanding()
	{
		return standing;
	}

	public void setStanding(String standing)
	{
		this.standing = standing;
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
	public String toString()
	{
		return "Staff [id=" + getId() + ", firstname=" + firstname + ", surname=" + surname + ", username=" + username
				+ ", email=" + email + ", phone=" + phone + ", mobile=" + mobile + ", title=" + title + ", timezone="
				+ timezone + ", position=" + position + ", access_level=" + access_level + ", financial_level="
				+ financial_level + "]";
	}

	
	@Override
	public int compareTo(Staff o)
	{
		return this.getUsername().compareTo(o.getUsername());
	}


}
