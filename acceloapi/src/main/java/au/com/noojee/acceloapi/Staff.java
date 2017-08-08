package au.com.noojee.acceloapi;

import java.io.IOException;

// import au.com.noojee.accelogateway.AcceloApi.EndPoints;

public class Staff
{
	private int id;
	private String firstname;
	private String surname;
	private String username;
	private String email;
	private String mobile;

	public static Staff getByEmail(AcceloApi acceloApi, String staffEmailAddress)
			throws AcceloException
	{
		Staff.ResponseList response = null;
		try
		{
			if (staffEmailAddress != null)
			{
                AcceloFilter filter = new AcceloFilter();
                filter.add(new AcceloFilter.SimpleMatch("email", staffEmailAddress));

				response = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.staff.getURL(), filter, AcceloFieldList.ALL, Staff.ResponseList.class);
			}
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Staff staff = null;
		if (response != null)
		{
			//staff = response.getList().size() > 0 ? response.getList().get(0) : null;

			for (Staff aStaff : response.getList())
			{
			    if (aStaff.getEmail().compareToIgnoreCase(staffEmailAddress) == 0)
			    {
			        staff = aStaff;
			        break;
			    }
			}
		}

		return staff;
	}

	public static Staff getById(AcceloApi acceloApi, int id)
			throws AcceloException
	{
		Staff.Response response = null;
		try
		{
			response = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.staff.getURL(id), null, AcceloFieldList.ALL, Staff.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Staff staff = response.getEntity();

		return staff;
	}

	public int getId()
	{
		return id;
	}

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

	public class Response extends AcceloResponse<Staff>
	{
	}

	public class ResponseList extends AcceloResponseList<Staff>
	{
	}

	@Override
	public String toString()
	{
		return "Staff [ id=" + id + " firstname=" + firstname + ", surname=" + surname  + ", username=" + username+ ", email=" + email +  "]";
	}

	public String getFullName()
	{
		return firstname + " " + surname;
	}

}
