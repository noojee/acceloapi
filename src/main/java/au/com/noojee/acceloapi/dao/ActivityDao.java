package au.com.noojee.acceloapi.dao;

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
import au.com.noojee.acceloapi.entities.meta.AgainstType_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ActivityDao extends AcceloDao<Activity>
{
	static private Logger logger = LogManager.getLogger(Activity.class);


	public List<Activity> getByTicket(Ticket ticket) throws AcceloException
	{
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType_.issue, ticket.getId()));
		
		return getByFilter(filter);
	}

	public void insert(Activity activity) 
	{
		AcceloFieldValues values = marshallArgs(activity);

		ActivityDao.Response response = AcceloApi.getInstance().insert(EndPoint.activities, values, ActivityDao.Response.class);
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

	
	/**
	 * As we can't update a lot of the attributes of an activity we resort
	 * to deleting it and recreating it.
	 * 
	 * To use this method, retrieve an activity from accelo.
	 * Adjust the details of the activity object and then call replace
	 * This method will delete the original activity using its id and then
	 * insert an new replacement activity based on the activity you pass in.
	 * @param activity
	 */
	public void replace(Activity activity)
	{
		delete(activity);
		insert(activity);
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
