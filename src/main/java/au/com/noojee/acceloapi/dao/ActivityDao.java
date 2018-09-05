package au.com.noojee.acceloapi.dao;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.meta.Activity_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ActivityDao extends AcceloDao<Activity>
{
	static private Logger logger = LogManager.getLogger(Activity.class);

	public List<Activity> getByTicket(Ticket ticket) throws AcceloException
	{
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType.issue, ticket.getId()));

		return getByFilter(filter);
	}

	/**
	 * Activities can be roughly categorized in to staff generated and system generated activities. unlike the method
	 * getActivities this method only returns activites which were logged by a staff member. Mostly we are only
	 * interested in staff generated activities (for the purposes of timesheets and ticket billable hours). As such you
	 * will normally want to exclude system activities. This method is generally more useful as there are a lot of
	 * system activites that for the most part you just don't care about.
	 * 
	 * @param ticket
	 * @param excludeSystemActivities if true then we exclude system activities from the result.
	 * @return
	 */
	public List<Activity> getByTicket(Ticket ticket, boolean excludeSystemActivities)
	{
		List<Activity> list = null;
		try
		{
			AcceloFilter<Activity> filter = new AcceloFilter<>();

			filter.where(filter.against(AgainstType.ticket, ticket.getId()));

			if (excludeSystemActivities)
				// If the staff field is 0 then this is a system generated activity.
				filter.and(filter.greaterThan(Activity_.staff, 0));

			list = new ActivityDao().getByFilter(filter);
		}
		catch (AcceloException e)
		{
			logger.error(e, e);
		}
		return list;
	}
	
	
	/**
	 * Return a list of activities for the given company that occurred on or after the given createdDate
	 * 
	 * @param company
	 * @param createdDate
	 * @return
	 */
	public List<Activity> getRecentByCompany(Company company, LocalDate createdDate)
	{
		List<Activity> list = null;
		try
		{
			AcceloFilter<Activity> filter = new AcceloFilter<>();

			filter.where(filter.against(AgainstType.company, company.getId()))
			.and(filter.after(Activity_.date_created, createdDate.atStartOfDay()))
			// If the staff field is 0 then this is a system generated activity so lets exclude it.
			.and(filter.greaterThan(Activity_.staff, 0));

			list = new ActivityDao().getByFilter(filter);
		}
		catch (AcceloException e)
		{
			logger.error(e, e);
		}
		return list;
	}
	
	
	/**
	 * Return a list of activities associated to a ticket that occurred on or after the given createdDate
	 * 
	 * We exclude system generated activities as they just clutter up the data set
	 * without adding any real value.
	 * 
	 * @param company
	 * @param createdDate
	 * @return
	 */
	public List<Activity> getRecentTicketActivities(LocalDate createdDate)
	{
		List<Activity> list = null;
		try
		{
			AcceloFilter<Activity> filter = new AcceloFilter<>();
			filter.noLimit();

			filter.where(filter.eq(Activity_.against_type, AgainstType.issue))
			.and(filter.afterOrEq(Activity_.date_created, createdDate.atStartOfDay()))
			// If the staff field is 0 then this is a system generated activity so lets exclude it.
			// we need system activities as we miss some tickets if no activity ever taken (goes straight to closed).
			.and(filter.greaterThan(Activity_.staff, 0));

			list = new ActivityDao().getByFilter(filter);
		}
		catch (AcceloException e)
		{
			logger.error(e, e);
		}
		return list;
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
