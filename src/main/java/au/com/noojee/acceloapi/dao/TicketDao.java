package au.com.noojee.acceloapi.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Affiliation;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.Priority;
import au.com.noojee.acceloapi.entities.Staff;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.meta.Ticket_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.util.Constants;
import au.com.noojee.acceloapi.util.StreamMaths;

public class TicketDao extends AcceloDao<Ticket>
{
	static private Logger logger = LogManager.getLogger(TicketDao.class);

	// The miniumum we bill for any ticket which is raised.
	public static final long MIN_BILL_INTERVAL = 5;
	// the amount of time we spend on an individual activity before we bill the MIN_BILL_INTERVAL
	public static final long BILLING_LEWAY = 0;

	/**
	 * TicketNo is the id.
	 * 
	 * @throws AcceloException
	 */
	@Override
	public Ticket getById(int ticketNo) throws AcceloException
	{
		return getById(EndPoint.tickets, ticketNo);
	}

	/**
	 * TicketNo is the id.
	 * 
	 * @throws AcceloException
	 */
	@Override
	public List<Ticket> getByFilter(AcceloFilter<Ticket> filter) throws AcceloException
	{
		return super.getByFilter(filter);
	}

	/**
	 * Returns a list of tickets attached to the passed contract.
	 * 
	 * @param acceloApi
	 * @param contract
	 * @return
	 * @throws AcceloException
	 */
	public List<Ticket> getByContract(Contract contract) throws AcceloException
	{
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.contract, contract.getId()));

		return this.getByFilter(filter);
	}

	/**
	 * Returns a list of tickets attached to the passed contract.
	 * 
	 * @param acceloApi
	 * @param contract
	 * @return
	 * @throws AcceloException
	 */
	public List<Ticket> getByCompany(Company company) throws AcceloException
	{
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType.company, company.getId()));

		return this.getByFilter(filter);
	}

	/**
	 * Returns the list of tikets
	 * 
	 * @param acceloApi
	 * @param contract
	 * @param firstDateOfInterest
	 * @return
	 * @throws AcceloException
	 */
	public List<Ticket> getRecentByContract(Contract contract, LocalDate firstDateOfInterest) throws AcceloException
	{
		// Get the day before the day of interest so we can just use isAfter
		LocalDateTime dayBefore = LocalDateTime.of(firstDateOfInterest.minusDays(1), LocalTime.of(0, 0));

		// Get tickets with a close date on or after the firstDateOfInterest
		// or with no close date.
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.contract, contract.getId())
				.and(filter.after(Ticket_.date_closed, dayBefore)
						.or(filter.before(Ticket_.date_closed, Constants.DATETIMEZERO))));

		return this.getByFilter(filter);
	}
	
	public List<Ticket> getAllOpen() throws AcceloException
	{
		// Get all tickets with no close date.
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.date_closed, Constants.DATETIMEZERO));

		return this.getByFilter(filter);
	}


	/**
	 * returns a list of tickets which haven't been assigned to a contract.
	 * 
	 * @param acceloApi
	 * @param company2
	 * @throws AcceloException
	 */
	public List<Ticket> getUnassigned(Company company) throws AcceloException
	{
		// Get the day before the day of interest so we can just use isAfter

		// Get tickets with a close date on or after the firstDateOfInterest
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.contract, 0).and(filter.against(AgainstType.company, company.getId())));

		return this.getByFilter(filter);

	}

	/**
	 * Assigns a staff member to a ticket and returns the new ticket.
	 */
	public Ticket assignStaff(Ticket ticket, Staff staff) throws AcceloException
	{
		Ticket result = null;
		try
		{
			ticket.setEngineerAssigned(staff.getId());
			result = this.update(ticket);

			if (result == null)
			{
				throw new AcceloException(
						"Failed to assign staff to ticket id:" + ticket.getId() + " details:" + this.toString());
			}
		}
		catch (Exception e)
		{
			throw new AcceloException(e);
		}
		return result;
	}

	public Contact getContact(Ticket ticket) throws AcceloException
	{
		Contact contact = null;

		Affiliation affiliation = new AffiliationDao().getById(ticket.getAffiliation());

		if (affiliation != null)
		{
			int contactId = affiliation.getContactId();

			contact = new ContactDao().getById(contactId);
		}
		return contact;
	}

	public Duration sumMTDWork(Ticket ticket)
	{
		Duration mtdWork = null;
		try
		{
			LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);

			List<Activity> activities = new ActivityDao().getByTicket(ticket);
			long work = activities.stream().filter(a ->
				{
					return a.getDateTimeCreated().toLocalDate().isAfter(startOfMonth)
							|| a.getDateTimeCreated().toLocalDate().isEqual(startOfMonth);
				}) // greater than or equal to first day of month.
					.mapToLong(a -> a.getBillable().plus(a.getNonBillable()).getSeconds()).sum();

			mtdWork = Duration.ofSeconds(work);
		}
		catch (AcceloException e)
		{
			logger.error(e, e);
		}
		return mtdWork;

	}

	public Duration sumLastMonthWork(Ticket ticket)
	{
		Duration lastMonthWork = null;
		try

		{
			List<Activity> activities = new ActivityDao().getByTicket(ticket);

			LocalDate startOfLastMonth = LocalDate.now().withDayOfMonth(1).minus(java.time.Period.ofMonths(1));

			long work = activities.stream().filter(a ->
				{
					return (a.getDateTimeCreated().toLocalDate().isAfter(startOfLastMonth) // greater than or equal to
																							// the 1st day of last
							// month.
							|| a.getDateTimeCreated().toLocalDate().isEqual(startOfLastMonth))
							&& a.getDateTimeCreated().toLocalDate().isBefore(LocalDate.now().withDayOfMonth(1)) // before
																												// the
																												// first
																												// day
																												// of
					// the current month.
					;
				}).mapToLong(a -> a.getBillable().plus(a.getNonBillable()).getSeconds()).sum();

			lastMonthWork = Duration.ofSeconds(work);
		}
		catch (AcceloException e)
		{
			logger.error(e, e);
		}
		return lastMonthWork;
	}

	/**
	 * Returns the total work on this ticket.
	 * 
	 * @return
	 */
	public Duration totalWork(Ticket ticket)
	{
		ActivityDao daoActivity = new ActivityDao();

		long work = daoActivity.getByTicket(ticket, true).stream()
				.mapToLong(a -> a.getBillable().plus(a.getNonBillable()).getSeconds()).sum();

		return Duration.ofSeconds(work);
	}

	/**
	 * Returns true if all Activities for this ticket have been approved or invoiced.
	 * 
	 * @return
	 * @throws AcceloException
	 */
	public boolean isWorkApproved(Ticket ticket) throws AcceloException
	{
		ActivityDao daoActivity = new ActivityDao();
		boolean isFullyApproved = daoActivity.getByTicket(ticket, true).stream().allMatch(a -> a.isApproved());
		return isFullyApproved;
	}

	public Duration getBillable(Ticket ticket)
	{
		ActivityDao daoActivity = new ActivityDao();
		return StreamMaths.sum(daoActivity.getByTicket(ticket, true).stream(), Activity::getBillable);
	}

	public Duration getNonBillable(Ticket ticket)
	{
		ActivityDao daoActivity = new ActivityDao();
		return StreamMaths.sum(daoActivity.getByTicket(ticket, true).stream(), Activity::getNonBillable);
	}

	/**
	 * Rounds billing up on activities to the nears rounding block (roundToMinutes). If the excess over the nearest
	 * rounding block is no more than leawayMinutes then we don't round up. The oldest activity is selected for
	 * adjustment, then deleted and replaced with the a new replacement activity that contains the correct billings.
	 * 
	 * @param a replacement activity for the activity that was adjusted.
	 */
	public Activity roundBilling(Ticket ticket, long roundToMinutes,
			long leawayMinutes)
	{

		ActivityDao daoActivity = new ActivityDao();

		List<Activity> activities = daoActivity.getByTicket(ticket, true);

		// Create a new activity with the additional time.
		return doRounding(ticket, roundToMinutes, leawayMinutes, activities);
	}

	private Activity doRounding(Ticket ticket, long roundToMinutes, long leawayMinutes,
			List<Activity> activities)
	{
		// calculate the current total billable for all activities against this ticket.
		Duration totalBillable = activities.parallelStream().map(Activity::getBillable).reduce(Duration.ZERO,
				(lhs, rhs) -> lhs.plus(rhs));

		long minutes = totalBillable.toMinutes();
		long targetMinutes = calcTargetMinutes(totalBillable, roundToMinutes, leawayMinutes);
		Activity oActivity = null;

		// If billable minutes is zero then we assume this ticket isn't billable unless its critical or urgent which are
		// always billable
		if ((minutes != 0
				|| ticket.getPriority() == Priority.NoojeePriority.Critical
				|| ticket.getPriority() == Priority.NoojeePriority.Urgent)
				&& targetMinutes != minutes)
		{
			// Calculate the amount of time for the new activity which will bring the total billable up to the required
			// lievel.
			Duration roundedBillable = Duration.ofMinutes(targetMinutes).minus(totalBillable);

			Activity activityToRoundUp = new Activity();
			activityToRoundUp.setAgainst(AgainstType.ticket, ticket.getId());
			activityToRoundUp.setOwner(ActivityOwnerType.staff, ticket.getAssignee());
			activityToRoundUp.setSubject("Opened Ticket  - Tracking: XACYKA");
			activityToRoundUp.setDetails("Tracking Code:  XACYKA(" + minutes * 3 + ")");
			activityToRoundUp.setDateTimeStarted(ticket.getDateTimeStarted());
			activityToRoundUp.setBillable(roundedBillable);

			oActivity = new ActivityDao().insert(activityToRoundUp);
		}

		return oActivity;
	}

	public boolean isBillAdjustmentRequired(Duration totalBillable, long roundToMinutes, long leawayMinutes)
	{
		long rounded = calcTargetMinutes(totalBillable, roundToMinutes, leawayMinutes);
		long minutes = totalBillable.toMinutes();

		return (minutes != 0 && rounded != minutes);
	}

	private long calcTargetMinutes(Duration totalBillable, long roundToMinutes, long leawayMinutes)
	{

		long minutes = totalBillable.toMinutes();
		long rounded = minutes;

		// We only round up if they are more than 5 minutes into the next block.
		long excess = minutes % roundToMinutes;
		if (minutes < roundToMinutes || excess > leawayMinutes)
			rounded = (long) (Math.ceil(minutes / (float) roundToMinutes) * roundToMinutes);

		return rounded;
	}
	
	
	/**
	 * marks all activities associated with the ticket as billable by making any 
	 * non-billable amounts billable.
	 * 
	 * @param ticket
	 */
	void makeAllActivitiesBillable(Ticket ticket)
	{
		/**
		 * We have to deal with the activity threads and parent relationships.
		 * 
		 */
		ActivityDao daoActivity = new ActivityDao();
		List<Activity> activities = daoActivity.getByTicket(ticket);
		
		// find the activity that owns the thread and ensure that there is only one or zero of these.
		@SuppressWarnings("unused")
		List<Activity> owner = getOriginals(activities);
		
		
	}

	private List<Activity> getOriginals(List<Activity> activities)
	{
		List<Activity> originals = new ArrayList<>();
		
		for (Activity activity : activities)
		{
			if (activity.getThreadId() == activity.getId())
			{
					originals.add(activity);
			}
		}
		return originals;
	}

	@SuppressWarnings("unused")
	private List<Activity> getParents(List<Activity> activities)
	{
		List<Activity> parents = new ArrayList<>();
		
		for (Activity activity : activities)
		{
			if (activity.getParentId() != 0)
			{
				parents.add(activity);
			}
		}
		return parents;
	}

	/**
	 * generates a URL into the Accelo UI for the approval screen.
	 * It checks to see if the ticket is attached to a contract and modifies the returned url
	 * to bring up the correct approval screen (ticket or contract).
	 * 
	 * @param domain
	 * @param ticket
	 * @return
	 */
	public URL getApproveURL(String domain, Ticket ticket)
	{
		URL acceloApproveURL = null;
		try
		{
			if (ticket.getContractId() == 0)
			{
				// Its not attached to a contract
				String action = "?action=approve_object&object_id=" + ticket.getId() + "&object_table=issue";
				acceloApproveURL = new URL("https", domain, 443, action);
			}
			else
			{
				// Its attached to a contract
				String action = "?action=contract_management&id=" + ticket.getContractId();

				acceloApproveURL = new URL("https", domain, 443, action);

			}
		}
		catch (MalformedURLException e)
		{

		}
		return acceloApproveURL;

	}


	@Override
	void preInsertValidation(Ticket ticket)
	{
		if (ticket.getTitle().isEmpty())
		{
			throw new AcceloException("You must provide a title");
		}

	}

	@Override
	protected AcceloFieldList getFieldList()
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("issue_status._ALL");
		// fields.add("priority._ALL");
		return fields;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.tickets;
	}

	@Override
	protected Class<Ticket> getEntityClass()
	{
		return Ticket.class;
	}

	public class Response extends AcceloResponse<Ticket>
	{
	}

	public class ResponseList extends AcceloResponseList<Ticket>
	{
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return TicketDao.ResponseList.class;
	}

	@Override
	protected Class<? extends AcceloResponse<Ticket>> getResponseClass()
	{
		return TicketDao.Response.class;
	}

	public URL getEditURL(String domain, Ticket ticket)
	{
		URL acceloEditURL = null;
		try
		{
			String action = "?action=edit_support_issue&id=" + ticket.getId();
			acceloEditURL = new URL("https", domain, 443, action);
		}
		catch (MalformedURLException e)
		{

		}
		return acceloEditURL;

	}

}
