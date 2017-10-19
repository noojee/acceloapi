package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.Staff;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.After;
import au.com.noojee.acceloapi.filter.expressions.Against;
import au.com.noojee.acceloapi.filter.expressions.Eq;
import au.com.noojee.acceloapi.filter.expressions.Expression;

public class TicketDao extends AcceloDao<Ticket, TicketDao.ResponseList>
{
	public class Response extends AcceloResponse<Ticket>
	{
	}

	public class ResponseList extends AcceloResponseList<Ticket>
	{
	}

	/**
	 * TicketNo is the id.
	 * 
	 * @throws AcceloException
	 */
	@Override
	public Ticket getById(AcceloApi api, int ticketNo) throws AcceloException
	{

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status(_ALL)");

		return getById(api, EndPoint.tickets, ticketNo, fields);

	}

	/**
	 * TicketNo is the id.
	 * 
	 * @throws AcceloException
	 */
	@Override
	public List<Ticket> getByFilter(AcceloApi api, AcceloFilter filter) throws AcceloException
	{

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status(_ALL)");

		return getByFilter(api, filter, fields);

	}

	/**
	 * Returns a list of tickets attached to the passed contract.
	 * 
	 * @param acceloApi
	 * @param contract
	 * @return
	 * @throws AcceloException
	 */
	public List<Ticket> getByContract(AcceloApi acceloApi, Contract contract) throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("contract", contract.getId()));

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status(_ALL)");
		
		return this.getByFilter(acceloApi, filter,fields);
	}

	/**
	 * Returns a list of tickets attached to the passed contract.
	 * 
	 * @param acceloApi
	 * @param contract
	 * @return
	 * @throws AcceloException
	 */
	public List<Ticket> getByCompany(AcceloApi acceloApi, Company company) throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Against("company", company.getId()));

		// against_id=3617, against_type=company,

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status(_ALL)");

		return this.getByFilter(acceloApi, filter,fields);
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
	public List<Ticket> getRecentByContract(AcceloApi acceloApi, Contract contract, LocalDate firstDateOfInterest)
			throws AcceloException
	{
		// Get the day before the day of interest so we can just use isAfter
		LocalDate dayBefore = firstDateOfInterest.minusDays(1);

		// Get tickets with a close date on or after the firstDateOfInterest
		// or with no close date.
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("contract", contract.getId())
				.and(new After("date_closed", dayBefore).or(new Eq("date_closed", Expression.DATE1970))));

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status(_ALL)");
		
		return this.getByFilter(acceloApi, filter,fields);
	}

	/**
	 * returns a list of tickets which haven't been assigned to a contract.
	 * 
	 * @param acceloApi
	 * @param company2
	 * @throws AcceloException
	 */
	public List<Ticket> getUnassigned(AcceloApi acceloApi, Company company) throws AcceloException
	{
		// Get the day before the day of interest so we can just use isAfter

		// Get tickets with a close date on or after the firstDateOfInterest
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("contract", 0).and(new Against("company", company.getId())));

		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status(_ALL)");

		
		return this.getByFilter(acceloApi, filter,fields);

	}

	public Ticket insert(AcceloApi acceloApi, Ticket ticket, Contact contacts, Company company)
			throws IOException, AcceloException
	{
		Ticket result = null;

		// Assign the Ticket to the owning aCompany.
		AcceloFieldValues fields = new AcceloFieldValues();
		fields.add("title", ticket.getTitle()); // URLEncoder.encode(ticket.getTitle()));
		fields.add("type_id", ticket.getType());

		fields.add("against_id", "" + contacts.getId());
		fields.add("against_type", "contact");

		fields.add("against_id", "" + company.getId());
		fields.add("against_type", "company");

		fields.add("status_id", ticket.getStatus().getId());
		fields.add("date_started", "" + (System.currentTimeMillis() / 1000));
		fields.add("date_entered", "" + (System.currentTimeMillis() / 1000));
		fields.add("description", ticket.getDescription());
		fields.add("class_id", "" + ticket.getClassId()); // Imported

		TicketDao.Response response = acceloApi.insert(EndPoint.tickets, fields, TicketDao.Response.class);
		if (response == null || response.getEntity() == null)
		{
			throw new AcceloException(
					"Failed to insert ticket id:" + ticket.getCustomId() + " details:" + this.toString());
		}

		result = response.getEntity();

		return result;

	}

	/**
	 * Assigns a staff member to a ticket and returns the new ticket.
	 */
	public Ticket assignStaff(AcceloApi acceloApi, Ticket ticket, Staff staff) throws AcceloException
	{
		Ticket result = null;
		try
		{

			// Assign the Ticket to the owning aCompany.
			AcceloFieldValues fields = new AcceloFieldValues();
			fields.add("assignee", staff.getId());

			TicketDao.Response response = acceloApi.update(EndPoint.tickets, ticket.getId(), fields,
					TicketDao.Response.class);
			if (response == null || response.getEntity() == null)
			{
				throw new AcceloException(
						"Failed to assign staff to ticket id:" + ticket.getId() + " details:" + this.toString());
			}

			result = response.getEntity();
		}
		catch (Exception e)
		{
			throw new AcceloException(e);
		}
		return result;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.tickets;
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return TicketDao.ResponseList.class;
	}

}
