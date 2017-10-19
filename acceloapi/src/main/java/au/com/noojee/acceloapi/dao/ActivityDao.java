package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Against;

public class ActivityDao extends AcceloDao<Activity, ActivityDao.ResponseList>
{
	static private Logger logger = LogManager.getLogger(Activity.class);

	public class Response extends AcceloResponse<Activity>
	{
	}

	public class ResponseList extends AcceloResponseList<Activity>
	{
	}

	public List<Activity> getByTicket(AcceloApi acceloApi, Ticket ticket) throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Against("issue", ticket.getId()));
		
		return getByFilter(acceloApi, filter);
	}

	public void insert(AcceloApi acceloApi, Activity activity) throws IOException, AcceloException
	{
		AcceloFieldValues values = marshallArgs(activity);

		ActivityDao.Response response = acceloApi.insert(EndPoint.activities, values, ActivityDao.Response.class);
		logger.debug(response);
	}

	private AcceloFieldValues marshallArgs(Activity activity)
	{
		AcceloFieldValues args = new AcceloFieldValues();

		args.add("subject", activity.getSubject());
		args.add("body", activity.getBody());
		args.add("against_id", "" + activity.getAgainstId());
		args.add("against_type", activity.getAgainstType());
		args.add("owner_id", "" + activity.getOwnerId());
		args.add("owner_type", activity.getOwnerType());
		args.add("medium", activity.getMedium().toString());
		args.add("visibility", activity.getVisiblity().toString());
		args.add("details", activity.getDetails());
		args.add("date_started", "" + activity.getDateStarted());
		args.add("date_created", "" + activity.getDateCreated());

		return args;
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ActivityDao.ResponseList.class;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.activities;
	}

}
