package au.com.noojee.acceloapi.entities;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.SearchFilterField;

public class AccountDetails extends AcceloEntity<AccountDetails>
{
    private String position;

    private String phone;

    private String fax;

    private String access_level;

    // private Locale locale;

    private String surname;
	
    private String firstname;

     private String username;

	@BasicFilterField
	@SearchFilterField
    private String title;

    private UserDefinedTitles user_defined_titles;

    private String initials;

    private String email;

    private UserAccess user_access;

    private String mobile;

 
	public String getPosition ()
    {
        return position;
    }

    public void setPosition (String position)
    {
        this.position = position;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getFax ()
    {
        return fax;
    }

    public void setFax (String fax)
    {
        this.fax = fax;
    }

    public String getAccess_level ()
    {
        return access_level;
    }

    public void setAccess_level (String access_level)
    {
        this.access_level = access_level;
    }

    public String getSurname ()
    {
        return surname;
    }

    public void setSurname (String surname)
    {
        this.surname = surname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

  
    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public UserDefinedTitles getUser_defined_titles ()
    {
        return user_defined_titles;
    }

    public void setUser_defined_titles (UserDefinedTitles user_defined_titles)
    {
        this.user_defined_titles = user_defined_titles;
    }

    public String getInitials ()
    {
        return initials;
    }

    public void setInitials (String initials)
    {
        this.initials = initials;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public UserAccess getUser_access ()
    {
        return user_access;
    }

    public void setUser_access (UserAccess user_access)
    {
        this.user_access = user_access;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [position = "+position+", phone = "+phone+", fax = "+fax+", access_level = "+access_level 
        		// +", locale = "+locale+"
        		+ "surname = "+surname+", firstname = "+firstname+", id = "+getId()+", username = "+username+", title = "+title+", user_defined_titles = "+user_defined_titles+", initials = "+initials+", email = "+email+", user_access = "+user_access+", mobile = "+mobile+"]";
    }

}
			
			