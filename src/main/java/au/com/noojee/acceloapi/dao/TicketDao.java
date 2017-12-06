package au.com.noojee.acceloapi.dao;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import au.com.noojee.acceloapi.entities.Staff;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.meta.Ticket_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.util.Constants;
import au.com.noojee.acceloapi.util.StreamMaths;
import au.com.noojee.acceloapi.util.Tuple;

public class TicketDao extends AcceloDao<Ticket>
{
	/**
	 * TicketNo is the id.
	 * 
	 * @throws AcceloException
	 */
	@Override
	public Ticket getById(int ticketNo) throws AcceloException
	{
		AcceloFieldList fields = getDefaultFields();

		return getById(EndPoint.tickets, ticketNo, fields);
	}

	/**
	 * TicketNo is the id.
	 * 
	 * @throws AcceloException
	 */
	@Override
	public List<Ticket> getByFilter(AcceloFilter<Ticket> filter) throws AcceloException
	{
		AcceloFieldList fields = getDefaultFields();

		return getByFilter(filter, fields);
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

		AcceloFieldList fields = getDefaultFields();
		return this.getByFilter(filter, fields);
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

		AcceloFieldList fields = getDefaultFields();
		return this.getByFilter(filter, fields);
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
		LocalDate dayBefore = firstDateOfInterest.minusDays(1);

		// Get tickets with a close date on or after the firstDateOfInterest
		// or with no close date.
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.contract, contract.getId())
				.and(filter.after(Ticket_.date_closed, dayBefore)
						.or(filter.before(Ticket_.date_closed, Constants.DATEZERO))));

		AcceloFieldList fields = getDefaultFields();
		return this.getByFilter(filter, fields);
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

		AcceloFieldList fields = getDefaultFields();
		return this.getByFilter(filter, fields);

	}

	/**
	 * Assigns a staff member to a ticket and returns the new ticket.
	 */
	public Ticket assignStaff(Ticket ticket, Staff staff) throws AcceloException
	{
		Ticket result = null;
		try
		{
			ticket.setAssignee(staff.getId());
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
					return a.getDateTimeCreated().toLocalDate().isAfter(startOfMonth) || a.getDateTimeCreated().toLocalDate().isEqual(startOfMonth);
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
					return (a.getDateTimeCreated().toLocalDate().isAfter(startOfLastMonth) // greater than or equal to the 1st day of last
																			// month.
							|| a.getDateTimeCreated().toLocalDate().isEqual(startOfLastMonth))
							&& a.getDateTimeCreated().toLocalDate().isBefore(LocalDate.now().withDayOfMonth(1)) // before the first day of
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
	 * Rounds billing up on activities to the nears rounding block (roundToMinutes).
	 * If the excess over the nearest rounding block is no more than leawayMinutes then we don't round up.
	 * 
	 * The oldest activity is selected for adjustment, then deleted and replaced with the a new replacement activity
	 * that contains the correct billings.
	 * 
	 * @param a replacement activity for the activity that was adjusted.
	 */
	public Tuple<Optional<Activity>, Optional<Activity>> roundBilling(Ticket ticket, long roundToMinutes, long leawayMinutes)
	{

		ActivityDao daoActivity = new ActivityDao();

		List<Activity> activities = daoActivity.getByTicket(ticket, true);

		// Find the oldest activity
		Optional<Activity> oldestActivity = activities.stream()
				.sorted((a, b) -> (a.getDateTimeCreated().isAfter(b.getDateTimeCreated()) ? 1 : -1)).findFirst();

		// Now add the time to the oldest activity.
		Optional<Activity> replacementActivity = oldestActivity
				.map(activity -> doRounding(roundToMinutes, leawayMinutes, activities, activity));

		return new  Tuple<>(oldestActivity, replacementActivity);
	}

	private Activity doRounding(long roundToMinutes, long leawayMinutes,
			List<Activity> activities, Activity selectedActivity)
	{
		// calculate the current total billable for all activities against this ticket.
		Duration totalBillable = activities.parallelStream().map(Activity::getBillable).reduce(Duration.ZERO,
				(lhs, rhs) -> lhs.plus(rhs));

		long minutes = totalBillable.toMinutes();
		long rounded = minutes;

		// We only round up if they are more than 5 minutes into the next block.
		long excess = minutes % roundToMinutes;
		if (minutes < roundToMinutes || excess > leawayMinutes)
			rounded = (long) (Math.ceil(minutes / (float) roundToMinutes) * roundToMinutes);

		Activity oActivity = null;
		if (rounded != minutes)
		{
			// Update the selected activity so the total billable for the ticket is rounded up to the nearest
			// fifteen minutes.
			Duration roundedBillable = Duration.ofMinutes(rounded).minus(totalBillable)
					.plus(selectedActivity.getBillable());
			selectedActivity.setBillable(roundedBillable);

			// We are about to destroy the original created date so if started date is zero
			// then lets push the created date into the started date.
			if (selectedActivity.getDateTimeStarted() == Constants.DATETIMEZERO)
				selectedActivity.setDateTimeStarted(selectedActivity.getDateTimeCreated());
			oActivity = new ActivityDao().replace(selectedActivity);
		}

		return oActivity;
	}

	@Override
	void preInsertValidation(Ticket ticket)
	{
		if (ticket.getTitle().isEmpty())
		{
			throw new AcceloException("You must provide a title");
		}

	}
	
	private AcceloFieldList getDefaultFields()
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status._ALL");
		//fields.add("priority._ALL");
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

}
