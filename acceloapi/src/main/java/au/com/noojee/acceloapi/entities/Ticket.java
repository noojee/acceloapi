package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloFilter;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.AcceloApi.EndPoints;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;
import au.com.noojee.acceloapi.AcceloFilter.SimpleMatch;

public class Ticket
{
	// private Logger logger = LogManager.getLogger(Ticket.class);
	static public String PRIORITY_CRITICAL = "1";

	public class Response extends AcceloResponse<Ticket>
	{
	}

	public class ResponseList extends AcceloResponseList<Ticket>
	{
	}

	private int id;
	private String title;
	private String custom_id;
	private String description;
	private String type;
	private int affiliation; // The affiliated with this ticket which links
								// through to the contact
	private String against;
	private int against_id;
	private String against_type;
	// private int aContact_id;
	private int company; // If against_type is company, the company the issue is
							// against.
	private String priority;
	private int classId;
	private int resolution;
	private Status status; // Breaks our rules of using Ids but there is no
							// other way to get the status.
	// private String status; // id of the status
	private String standing;
	private String submitted_by;
	private long date_submitted;
	private long date_opened;
	private long date_resolved;
	private long date_closed;
	private long date_started;
	private long date_due;
	private String closed_by;
	private String opened_by;
	private String resolved_by;
	@SuppressWarnings("unused")
	private String object_budget;
	private String assignee;
	private int billable_seconds;
	private long date_last_interacted;
	private ArrayList<String> breadcrumbs;
	private String contract;
	private String resolution_detail;

	// these are related objects that we need to fetch separately and we then
	// cache them here for fast access.
	private Contact cacheContact;
	private Company cacheCompany;

	public static List<Ticket> getTickets(AcceloApi acceloApi) throws AcceloException
	{

		AcceloResponseList<Ticket> response = acceloApi.getAll(AcceloApi.EndPoints.tickets, Ticket.ResponseList.class);
		return response.getList();

	}

	/**
	 * TicketNo is the id.
	 */
	static public Ticket getByTicketNo(AcceloApi api, int ticketNo) throws AcceloException
	{
		Ticket ticket = null;

		if (ticketNo != 0)
		{
			Ticket.ResponseList response;
			try
			{
				AcceloFilter filter = new AcceloFilter();
				filter.add(new AcceloFilter.SimpleMatch("id", ticketNo));

				AcceloFieldList fields = new AcceloFieldList();
				fields.add("_ALL");
				fields.add("status(_ALL)");

				response = api.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.tickets.getURL(), filter, fields,
						Ticket.ResponseList.class);
			}
			catch (IOException e)
			{
				throw new AcceloException(e);
			}

			if (response != null)
				ticket = response.getList().size() > 0 ? response.getList().get(0) : null;
		}

		return ticket;
	}

	/**
	 * Returns a list of tickets attached to the passed contract.
	 * 
	 * @param acceloApi
	 * @param contract
	 * @return
	 * @throws AcceloException
	 */
	public static List<Ticket> getByContract(AcceloApi acceloApi, Contract contract) throws AcceloException
	{
		List<Ticket> tickets = new ArrayList<>();

		Ticket.ResponseList response;
		try
		{
			AcceloFilter filter = new AcceloFilter();
			filter.add(new AcceloFilter.SimpleMatch("contract", contract.getId()));

			AcceloFieldList fields = new AcceloFieldList();
			fields.add("_ALL");
			fields.add("status(_ALL)");

			URL url = AcceloApi.EndPoints.tickets.getURL();

			boolean more = true;
			int page = 0;
			while (more)
			{
				URL pagedURL = new URL(url + "?_page=" + page + "&_limit=50");
				response = acceloApi.pull(AcceloApi.HTTPMethod.GET, pagedURL, filter, fields,
						Ticket.ResponseList.class);
				if (response != null)
				{
					List<Ticket> responseList = response.getList();

					// If we get less than a page we must now have everything.
					if (responseList.size() < 10)
						more = false;

					tickets.addAll(responseList);
					page += 1;
				}

			}
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return tickets;

	}

	public Ticket insert(AcceloApi acceloApi) throws IOException, AcceloException
	{
		Ticket result = null;

		// Assign the Ticket to the owning aCompany.
		AcceloFieldValues fields = new AcceloFieldValues();
		fields.add("title", this.getTitle()); // URLEncoder.encode(ticket.getTitle()));
		fields.add("type_id", getType());

		if (this.cacheContact != null)
		{
			fields.add("against_id", "" + cacheContact.getid());
			fields.add("against_type", "contact");
		}

		if (this.cacheCompany == null)
		{
			// then assign the ticket to Noojee.
			this.cacheCompany = Company.getByName(acceloApi, "Noojee Contact Solutions Pty Ltd");
		}
		if (this.cacheCompany != null)
		{

			fields.add("against_id", "" + cacheCompany.getId());
			fields.add("against_type", "company");

			fields.add("status_id", this.getStatus().getId());
			fields.add("date_started", "" + (System.currentTimeMillis() / 1000));
			fields.add("date_entered", "" + (System.currentTimeMillis() / 1000));
			fields.add("description", this.getDescription());
			fields.add("class_id", "" + getClassId()); // Imported
			// arguments.put("assignee", "2"); // assign to brett

			// logger.error("fields" + fields);

			Ticket.Response response = acceloApi.push(AcceloApi.HTTPMethod.POST, AcceloApi.EndPoints.tickets.getURL(),
					fields, Ticket.Response.class);
			if (response == null || response.getEntity() == null)
			{
				throw new AcceloException(
						"Failed to insert ticket id:" + this.custom_id + " details:" + this.toString());
			}

			result = response.getEntity();
		}

		return result;

	}

	/**
	 * Assigns a staff member to a ticket and returns the new ticket.
	 */
	public Ticket assignStaff(AcceloApi acceloApi, Staff staff) throws AcceloException
	{
		Ticket result = null;
		try
		{

			// Assign the Ticket to the owning aCompany.
			AcceloFieldValues fields = new AcceloFieldValues();
			fields.add("assignee", staff.getId());

			Ticket.Response response = acceloApi.push(AcceloApi.HTTPMethod.PUT,
					AcceloApi.EndPoints.tickets.getURL(this.id), fields, Ticket.Response.class);
			if (response == null || response.getEntity() == null)
			{
				throw new AcceloException(
						"Failed to assign staff to ticket id:" + this.id + " details:" + this.toString());
			}

			result = response.getEntity();
		}
		catch (Exception e)
		{
			throw new AcceloException(e);
		}
		return result;
	}

	// Returns a list of activities associated with this ticket.
	public List<Activity> getActivities(AcceloApi acceloApi) throws AcceloException
	{
		return Activity.getByTicket(acceloApi, this);
	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return trim(title);
	}

	private String trim(String field)
	{
		return (field == null ? null : field.trim());
	}

	public String getCustom_id()
	{
		return custom_id;
	}

	public String getDescription()
	{
		return trim(description);
	}

	public String getType()
	{
		return type;
	}

	public String getAgainst()
	{
		return against;
	}

	public int getAgainst_id()
	{
		return against_id;
	}

	public String getAgainst_type()
	{
		return against_type;
	}

	public Contact getContact(AcceloApi acceloApi) throws AcceloException
	{
		if (cacheContact == null)
		{
			Affiliation affiliation = Affiliation.getById(acceloApi, this.affiliation);

			if (affiliation != null)
			{
				int contactId = affiliation.getContactId();

				cacheContact = Contact.getById(acceloApi, contactId);
			}
		}
		return cacheContact;
	}

	public String getPriority()
	{
		return priority;
	}

	public int getClassId()
	{
		return classId;
	}

	public int getResolution()
	{
		return resolution;
	}

	public Status getStatus()
	{
		return status;
	}

	public String getStanding()
	{
		return standing;
	}

	public String getSubmitted_by()
	{
		return submitted_by;
	}

	public Date getDate_submitted()
	{
		return new Date(date_submitted * 1000);
	}

	public Date getDate_opened()
	{
		return new Date(date_opened * 1000);
	}

	public Date getDate_resolved()
	{
		return new Date(date_resolved * 1000);
	}

	// Returns null if the ticket is still open.
	public Date getDate_closed()
	{
		if (date_closed == 0)
			return null;
		else
			return new Date(date_closed * 1000);
	}

	public Date getDate_started()
	{
		return new Date(date_started * 1000);
	}

	public Date getDate_due()
	{
		return new Date(date_due * 1000);
	}

	public String getClosed_by()
	{
		return closed_by;
	}

	public String getOpened_by()
	{
		return opened_by;
	}

	public String getResolved_by()
	{
		return resolved_by;
	}

	public int getCompanyName()
	{
		return company;
	}

	public String getObject_budget()
	{
		return object_budget;
	}

	/**
	 * The staff id or -1 if no assigned engineer.
	 */
	public int getAssignee()
	{
		if (assignee != null)
			return Integer.valueOf(assignee);
		else
			return -1;
	}

	public int getBillable_seconds()
	{
		return billable_seconds;
	}

	public Date getDate_last_interacted()
	{
		return new Date(date_last_interacted * 1000);
	}

	public ArrayList<String> getBreadcrumbs()
	{
		return breadcrumbs;
	}

	public String getContract()
	{
		return contract;
	}

	public String getResolution_detail()
	{
		return trim(resolution_detail);
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setCustom_id(String custom_id)
	{
		this.custom_id = custom_id;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setAgainst(String against)
	{
		this.against = against;
	}

	public void setAgainst_id(int against_id)
	{
		this.against_id = against_id;
	}

	public void setAgainst_type(String against_type)
	{
		this.against_type = against_type;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public void setClass(int classId)
	{
		this.classId = classId;
	}

	public void setResolution(int resolution)
	{
		this.resolution = resolution;
	}

	public void setStanding(String standing)
	{
		this.standing = standing;
	}

	public void setSubmitted_by(String submitted_by)
	{
		this.submitted_by = submitted_by;
	}

	public void setDate_submitted(long date_submitted)
	{
		this.date_submitted = date_submitted;
	}

	public void setDate_opened(long date_opened)
	{
		this.date_opened = date_opened;
	}

	public void setDate_resolved(long date_resolved)
	{
		this.date_resolved = date_resolved;
	}

	public void setDate_closed(long date_closed)
	{
		this.date_closed = date_closed;
	}

	public void setDate_started(long date_started)
	{
		this.date_started = date_started;
	}

	public void setDate_due(long date_due)
	{
		this.date_due = date_due;
	}

	public void setClosed_by(String closed_by)
	{
		this.closed_by = closed_by;
	}

	public void setOpened_by(String opened_by)
	{
		this.opened_by = opened_by;
	}

	public void setResolved_by(String resolved_by)
	{
		this.resolved_by = resolved_by;
	}

	public void setCompanyId(int companyId)
	{
		this.company = companyId;
	}

	public void setObject_budget(String object_budget)
	{
		this.object_budget = object_budget;
	}

	public void setBillable_seconds(int billable_seconds)
	{
		this.billable_seconds = billable_seconds;
	}

	public void setDate_last_interacted(long date_last_interacted)
	{
		this.date_last_interacted = date_last_interacted;
	}

	public void setBreadcrumbs(ArrayList<String> breadcrumbs)
	{
		this.breadcrumbs = breadcrumbs;
	}

	public void setContract(String contract)
	{
		this.contract = contract;
	}

	public void setResolution_detail(String resolution_detail)
	{
		this.resolution_detail = resolution_detail;
	}

	@Override
	public String toString()
	{
		return "Ticket [id=" + id + ", title=" + title + ", custom_id=" + custom_id + ", type=" + type + ", against="
				+ against + ", against_id=" + against_id + ", against_type=" + against_type + ", priority=" + priority
				+ ", resolution=" + resolution + ", status=" + status + ", standing=" + standing + ", submitted_by="
				+ submitted_by + ", date_submitted=" + date_submitted + ", date_opened=" + date_opened
				+ ", date_resolved=" + date_resolved + ", date_closed=" + date_closed + ", date_started=" + date_started
				+ ", date_due=" + date_due + ", closed_by=" + closed_by + ", opened_by=" + opened_by + ", resolved_by="
				+ resolved_by + ", company=" + company + ", object_budget=" + object_budget + ", assignee=" + assignee
				+ ", billable_seconds=" + billable_seconds + ", date_last_interacted=" + date_last_interacted
				+ ", breadcrumbs=" + breadcrumbs + ", contract=" + contract + ", resolution_detail=" + resolution_detail
				+ ", contact=" + cacheContact + ", company=" + cacheCompany + ", description=" + description + "]";
	}

	public int getContactId()
	{
		return id;
	}

}
