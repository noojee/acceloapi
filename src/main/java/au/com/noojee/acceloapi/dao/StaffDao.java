package au.com.noojee.acceloapi.dao;

import java.util.List;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Staff;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

public class StaffDao extends AcceloDao<Staff>
{

	public Staff getByEmail(String staffEmailAddress) throws AcceloException
	{
		Staff staffMember = null;
		if (staffEmailAddress != null)
		{
			AcceloFilter filter = new AcceloFilter();
			filter.where(new Eq("email", staffEmailAddress));

			List<Staff> staff = this.getByFilter(filter);

			staffMember = staff.get(0);

		}
		return staffMember;

	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.staff;
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}

	public class Response extends AcceloResponse<Staff>
	{
	}

	public class ResponseList extends AcceloResponseList<Staff>
	{
	}

}
