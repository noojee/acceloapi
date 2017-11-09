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
import au.com.noojee.acceloapi.entities.meta.AgainstType_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ActivityDao extends AcceloDao<Activity>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(Activity.class);


	public List<Activity> getByTicket(Ticket ticket) throws AcceloException
	{
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType_.issue, ticket.getId()));
		
		return getByFilter(filter);
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
		// we do the insert first so if anything goes wrong we don't loose data.
		insert(activity);

		delete(activity);
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
