package au.com.noojee.acceloapi.entities;
public class Account_details
{
    private String position;

    private String phone;

    private String fax;

    private String access_level;

    private Locale locale;

    private String surname;

    private String firstname;

    private String id;

    private String username;

    private String title;

    private User_defined_titles user_defined_titles;

    private String initials;

    private String email;

    private User_access user_access;

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

    public Locale getLocale ()
    {
        return locale;
    }

    public void setLocale (Locale locale)
    {
        this.locale = locale;
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

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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

    public User_defined_titles getUser_defined_titles ()
    {
        return user_defined_titles;
    }

    public void setUser_defined_titles (User_defined_titles user_defined_titles)
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

    public User_access getUser_access ()
    {
        return user_access;
    }

    public void setUser_access (User_access user_access)
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
        return "ClassPojo [position = "+position+", phone = "+phone+", fax = "+fax+", access_level = "+access_level+", locale = "+locale+", surname = "+surname+", firstname = "+firstname+", id = "+id+", username = "+username+", title = "+title+", user_defined_titles = "+user_defined_titles+", initials = "+initials+", email = "+email+", user_access = "+user_access+", mobile = "+mobile+"]";
    }
}
			
			