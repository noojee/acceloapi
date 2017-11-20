package au.com.noojee.acceloapi.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ActivityDao extends AcceloDao<Activity>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(Activity.class);

	public List<Activity> getByTicket(Ticket ticket) throws AcceloException
	{
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType.issue, ticket.getId()));

		return getByFilter(filter);
	}

	@Override
	void preInsertValidation(Activity activity)
	{
		if (activity.getBillable() != null && activity.getOwnerType() == null)
		{
			throw new AcceloException("Validation Error: when setting a billable amount you must set the OwnerType");
		}
		
		if (activity.getSubject().isEmpty())
		{
			throw new AcceloException("You must provide a subject");
		}

	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.activities;
	}

	@Override
	protected Class<Activity> getEntityClass()
	{
		return Activity.class;
	}

	public class Response extends AcceloResponse<Activity>
	{
	}

	public class ResponseList extends AcceloResponseList<Activity>
	{
	}

	@Override
	protected Class<? extends AcceloResponseList<Activity>> getResponseListClass()
	{
		return ResponseList.class;
	}

	@Override
	protected Class<? extends AcceloResponse<Activity>> getResponseClass()
	{
		return Response.class;
	}

}
