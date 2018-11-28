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
	
	
	public List<Activity> getRecentTicketActivities(LocalDate extractionDate, int offset, int limit)
	{
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.offset(offset);
		filter.limit(limit);
		return getRecentTicketActivities(filter, extractionDate);
	}

	
	public List<Activity> getRecentTicketActivities(LocalDate extractionDate)
	{
		AcceloFilter<Activity> filter = new AcceloFilter<>();
		filter.noLimit();
		return getRecentTicketActivities(filter, extractionDate);
	}

			
	/**
	 * Return a list of activities associated to a ticket that occurred on or after the given createdDate
	 * 
	 * We include system activities as in some cases its the only activity we see so if we exclude
	 * the activity the associated ticket will never be picked up.
	 * 
	 * @param company
	 * @param extractionDate return all activities from this date.
	 * @return
	 */
	private List<Activity> getRecentTicketActivities(AcceloFilter<Activity> filter, LocalDate extractionDate)
	{
		List<Activity> list = null;
		try
		{
	
			// customer generated activities
			filter.where(filter.eq(Activity_.against_type, AgainstType.issue))
			.and(filter.eq(Activity_.owner_type, ActivityOwnerType.affiliation))
			.and(filter.eq(Activity_.staff, 0))
			.and(filter.eq(Activity_.date_created, extractionDate.atStartOfDay()));
			list = new ActivityDao().getByFilter(filter);
			logger.error("getRecentTicketActivities(customer): " + filter);
			
			// staff and system generated activities
			// we need system activities as we miss some tickets if no activity ever taken (goes straight to closed).
			// system generated activities have a '0' value for the staff field.
			filter.where(filter.eq(Activity_.against_type, AgainstType.issue))
			.and(filter.eq(Activity_.owner_type, ActivityOwnerType.staff))
			// .and(filter.greaterThan(Activity_.staff, 0))
			.and(filter.eq(Activity_.date_created, extractionDate.atStartOfDay()));
			
			list.addAll(new ActivityDao().getByFilter(filter));
			logger.error("getRecentTicketActivities(staff): " + filter);
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
