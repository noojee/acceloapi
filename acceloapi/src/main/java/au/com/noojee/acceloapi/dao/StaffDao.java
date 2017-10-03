package au.com.noojee.acceloapi.dao;

import java.io.IOException;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Staff;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

public class StaffDao extends AcceloDao<Staff, StaffDao.ResponseList>
{

	public Staff getByEmail(AcceloApi acceloApi, String staffEmailAddress) throws AcceloException
	{
		StaffDao.ResponseList response = null;
		try
		{
			if (staffEmailAddress != null)
			{
				AcceloFilter filter = new AcceloFilter();
				filter.add(new Eq("email", staffEmailAddress));

				response = acceloApi.get(EndPoint.staff, filter, AcceloFieldList.ALL, StaffDao.ResponseList.class);
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
