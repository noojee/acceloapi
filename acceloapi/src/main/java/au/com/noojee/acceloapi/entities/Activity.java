package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

public class Activity
{

	static private Logger logger = LogManager.getLogger(Activity.class);

	public enum Medium
	{
		note, meeting, report, email, call, postal, fax, sms
	};

	public enum Visibility
	{
		all("all"), confidential("confidential"), _private("private");

		String name;

		Visibility(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return this.name;

		}
	};

	private int id;
	private String subject;
	private String parent;
	private String parent_id;

	private String thread;
	private String thread_id;

	private String against_type;
	private int against_id;

	private int owner_id;
	private String owner_type;

	private Medium medium;
	private String body;
	private Visibility visiblity;

	private String details; // For meetings this is the location, for postals
	// this is the address and for calls this is the
	// number.

	private String standing; // The standing of the activity, may be one of
								// “unapproved”, “approved”, “invoiced”,
								// “locked”, or empty.
	private int invoice_id;
	private int contract_period_id;

	private long date_created;
	private long date_started;
	private long date_ended;
	private long date_logged;
	private long date_modified;

	private int billable; // amount of billable time logged for the activity in
							// seconds.
	private int nonbillable; // amount of non-billable seconds.

	private int staff;
	private int priority;
	private int _class;
	private int task;

	private int time_allocation; // The time allocation for the activity.
	private int rate; // id of the rate object
	private int rate_charged; // The rate at which the billable time was
								// charged.

	List<Tag> tag; // A list of tags associated with the activity

	// private String from_addr;
	// private String from_name;
	// private String to_addr;
	// private String to_name;
	//

	public void insert(AcceloApi acceloApi) throws IOException, AcceloException
	{
		AcceloFieldValues values = marshallArgs();

		Ticket.Response response = acceloApi.insert(EndPoint.activities, values, Ticket.Response.class);
		logger.debug(response);
	}

	private AcceloFieldValues marshallArgs()
	{
		AcceloFieldValues args = new AcceloFieldValues();

		args.add("subject", subject);
		args.add("body", body);
		args.add("against_id", "" + against_id);
		args.add("against_type", against_type);
		args.add("owner_id", "" + owner_id);
		args.add("owner_type", owner_type);
		args.add("medium", medium.toString());
		args.add("visibility", visiblity.toString());
		args.add("details", details);
		// args.put("priority_id", "" + priority_id);
		// args.put("class_id", "" + _class_id);
		// args.put("thread_id", "" + thread_id);
		// args.put("task_id", "" + task_id);
		// args.put("parent_id", "" + against_id);
		args.add("date_started", "" + date_started);
		// args.put("date_ended", "" + date_ended);
		args.add("date_created", "" + date_created);
		// args.put("staff_id", "" + staff_id);

		return args;
	}

	public static List<Activity> getByTicket(AcceloApi acceloApi, Ticket ticket) throws AcceloException
	{
		List<Activity> activities = new ArrayList<>();

		try
		{
			AcceloFilter filter = new AcceloFilter();
			filter.add(new Eq("against_id", ticket.getId()));

			AcceloFieldList fields = new AcceloFieldList();
			fields.add("_ALL");

			activities = acceloApi.getAll(EndPoint.activities, filter, fields, Activity.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return activities;

	}

	public List<Affiliation> getAffiliation(AcceloApi acceloApi) throws AcceloException
	{
		return Affiliation.getByActivity(acceloApi, this);
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void setAgainstId(int id)
	{
		this.against_id = id;
	}

	public void setAgainst_type(String against_type)
	{
		this.against_type = against_type;
	}

	public void setOwnerId(int id)
	{
		this.owner_id = id;
	}

	public void setOwner_type(String owner_type)
	{
		this.owner_type = owner_type;
	}

	public void setMedium(Medium medium)
	{
		this.medium = medium;
	}

	public void setVisiblity(Visibility visiblity)
	{
		this.visiblity = visiblity;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	public void setPriority(int priorityId)
	{
		this.priority = priorityId;
	}

	public void setClass(int ClassId)
	{
		this._class = ClassId;
	}

	public void setThreadId(String threadId)
	{
		this.thread_id = threadId;
	}

	public void setTaskId(int taskId)
	{
		this.task = taskId;
	}

	public void setParentId(String parentId)
	{
		this.parent_id = parentId;
	}

	public void setDateStarted(LocalDate dateStarted)
	{
		this.date_started = AcceloApi.toDateAsLong(dateStarted);
	}

	public void setDateEnded(LocalDate dateEnded)
	{
		this.date_ended = AcceloApi.toDateAsLong(dateEnded);
	}

	public void setDateCreated(LocalDate dateCreated)
	{
		this.date_created = AcceloApi.toDateAsLong(dateCreated);
	}

	public void setDateModified(LocalDate dateModified)
	{
		this.date_modified = AcceloApi.toDateAsLong(dateModified);
	}

	public void setStaffId(int staffId)
	{
		this.staff = staffId;
	}

	public int getId()
	{
		return id;
	}

	public String getSubject()
	{
		return subject;
	}

	public String getParent()
	{
		return parent_id;
	}

	public String getThread()
	{
		return thread_id;
	}

	public int getAgainst()
	{
		return against_id;
	}

	public String getAgainst_type()
	{
		return against_type;
	}

	public int getOwner()
	{
		return owner_id;
	}

	public Medium getMedium()
	{
		return medium;
	}

	public String getBody()
	{
		return body;
	}

	public String getDetails()
	{
		return details;
	}

	public LocalDate getDateCreated()
	{
		return AcceloApi.toLocalDate(date_created);
	}

	public LocalDate getDateStarted()
	{
		return AcceloApi.toLocalDate(date_started);
	}

	public LocalDate getDateEnded()
	{
		return AcceloApi.toLocalDate(date_ended);
	}

	public LocalDate getDateModified()
	{
		return AcceloApi.toLocalDate(date_modified);
	}

	public int getStaff()
	{
		return staff;
	}

	public int getPriority()
	{
		return priority;
	}

	public int getActivityClass()
	{
		return _class;
	}

	public int getTask()
	{
		return task;
	}

	public String getOwnerType()
	{
		return this.owner_type;
	}

	// public void setFrom(String fromAddr, String from_name)
	// {
	// this.fromAddr = fromAddr;
	// this.from_name = from_name;
	//
	// }
	//
	//
	// public void setTo(String toAddr, String to_name)
	// {
	// this.toAddr = toAddr;
	// this.to_name = to_name;
	// }
	//

	public class Response extends AcceloResponse<Activity>
	{
	}

	public class ResponseList extends AcceloResponseList<Activity>
	{
	}

	@Override
	public String toString()
	{
		return "Activity [id=" + id + ", subject=" + subject + ", parent=" + parent + ", parent_id=" + parent_id
				+ ", thread=" + thread + ", thread_id=" + thread_id + ", against_type=" + against_type + ", against_id="
				+ against_id + ", owner_id=" + owner_id + ", owner_type=" + owner_type + ", medium=" + medium
				+ ", body=" + body + ", visiblity=" + visiblity + ", details=" + details + ", date_created="
				+ date_created + ", date_started=" + date_started + ", date_ended=" + date_ended + ", date_logged="
				+ date_logged + ", date_modified=" + date_modified + ", billable=" + billable + ", nonbillable="
				+ nonbillable + ", staff=" + staff + ", priority=" + priority + ", _class=" + _class + ", task=" + task
				+ ", time_allocation=" + time_allocation + ", rate=" + rate + ", rate_charged=" + rate_charged
				+ ", tag=" + tag + "]";
	}

	public String getStanding()
	{
		return standing;
	}

	public void setStanding(String standing)
	{
		this.standing = standing;
	}

	public int getInvoiceId()
	{
		return invoice_id;
	}

	public void setInvoiceId(int invoiceId)
	{
		this.invoice_id = invoiceId;
	}

	public int getContractPeriodId()
	{
		return contract_period_id;
	}

	public void setContractPeriodId(int contractPeriodId)
	{
		this.contract_period_id = contractPeriodId;
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public String getParentId()
	{
		return parent_id;
	}

	public String getThreadId()
	{
		return thread_id;
	}

	public int getAgainstId()
	{
		return against_id;
	}

	public int getOwnerId()
	{
		return owner_id;
	}

	public String getOwner_type()
	{
		return owner_type;
	}

	public Visibility getVisiblity()
	{
		return visiblity;
	}

	public long getDateLogged()
	{
		return date_logged;
	}

	public int getBillable()
	{
		return billable;
	}

	public int getNonbillable()
	{
		return nonbillable;
	}

	public int getTimeAllocation()
	{
		return time_allocation;
	}

	public int getRateId()
	{
		return rate;
	}

	public int getRateCharged()
	{
		return rate_charged;
	}

	public List<Tag> getTag()
	{
		return tag;
	}

}
