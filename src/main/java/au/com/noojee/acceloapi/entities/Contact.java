package au.com.noojee.acceloapi.entities;

public class Contact extends AcceloEntity<Contact>
{
	public static final String FIELDS_ALL = "contact._ALL";
	private int id;
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
	private int default_affiliation; // id of the contacts affiliation. Will normally be the company they are primarily associated with.
	private String communication;
	private String invoice_method;


	@Override
	public int getId()
	{
		return id;
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
		return "Contact [id=" + id +  ", firstname=" + firstname + ", middlename=" + middlename
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
		Contact other = (Contact) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	@Override
	public int compareTo(Contact o)
	{
		return this.getSurname().compareTo(o.getSurname());
	}




	
}
