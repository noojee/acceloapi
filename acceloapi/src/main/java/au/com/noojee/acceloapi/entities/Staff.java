package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.util.HashMap;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

// import au.com.noojee.accelogateway.AcceloApi.EndPoints;

public class Staff
{
	private int id;
	private String firstname;
	private String surname;
	private String username;
	private String email;
	private String phone;
	private String mobile;
	private String title;
	private String timezone;
	private String position;
	private String access_level;
	private String financial_level;

	// Staff don't change very often.
	static HashMap<Integer, Staff> staffCache = new HashMap<>();

	public static Staff getByEmail(AcceloApi acceloApi, String staffEmailAddress) throws AcceloException
	{
		Staff.ResponseList response = null;
		try
		{
			if (staffEmailAddress != null)
			{
				AcceloFilter filter = new AcceloFilter();
				filter.add(new Eq("email", staffEmailAddress));

				response = acceloApi.get(EndPoint.staff, filter, AcceloFieldList.ALL, Staff.ResponseList.class);
			}
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Staff staff = null;
		if (response != null)
		{
			// staff = response.getList().size() > 0 ? response.getList().get(0)
			// : null;

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

	public static Staff getById(AcceloApi acceloApi, int staff_id) throws AcceloException
	{
		Staff staff = staffCache.get(staff_id);

		if (staff == null && staff_id != 0)
		{
			Staff.ResponseList response = null;
			try
			{
				AcceloFilter filter = new AcceloFilter();
				filter.add(new Eq("id", staff_id));

				response = acceloApi.get(EndPoint.staff, filter, AcceloFieldList.ALL, Staff.ResponseList.class);
			}
			catch (IOException e)
			{
				throw new AcceloException(e);
			}

			if (!response.getList().isEmpty())
				staff = response.getList().get(0);

			if (staff != null)
				staffCache.put(staff.getId(), staff);
		}

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
		return "Staff [id=" + id + ", firstname=" + firstname + ", surname=" + surname + ", username=" + username
				+ ", email=" + email + ", phone=" + phone + ", mobile=" + mobile + ", title=" + title + ", timezone="
				+ timezone + ", position=" + position + ", access_level=" + access_level + ", financial_level="
				+ financial_level + "]";
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

	public static HashMap<Integer, Staff> getStaffCache()
	{
		return staffCache;
	}

}
