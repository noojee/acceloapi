package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloApi.EndPoints;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloFilter;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;

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

		Ticket.Response response = acceloApi.push(HTTPMethod.POST, EndPoints.activities.getURL(), values,
				Ticket.Response.class);
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

		Activity.ResponseList response;
		try
		{
			AcceloFilter filter = new AcceloFilter();
			filter.add(new AcceloFilter.SimpleMatch("against_id", ticket.getId()));

			AcceloFieldList fields = new AcceloFieldList();
			fields.add("_ALL");

			URL url = AcceloApi.EndPoints.activities.getURL();

			boolean more = true;
			int page = 0;
			while (more)
			{
				URL pagedURL = new URL(url + "?_page=" + page + "&_limit=50");
				response = acceloApi.pull(AcceloApi.HTTPMethod.GET, pagedURL, filter, fields,
						Activity.ResponseList.class);
				if (response != null)
				{
					List<Activity> responseList = response.getList();

					// If we get less than a page we must now have everything.
					if (responseList.size() < 10)
						more = false;

					activities.addAll(responseList);
					page += 1;
				}

			}
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

	public void setAgainst_id(int id)
	{
		this.against_id = id;
	}

	public void setAgainst_type(String against_type)
	{
		this.against_type = against_type;
	}

	public void setOwner_id(int id)
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

	public void setPriority(int priority_id)
	{
		this.priority = priority_id;
	}

	public void set_class(int _class_id)
	{
		this._class = _class_id;
	}

	public void setThread_id(String thread_id)
	{
		this.thread_id = thread_id;
	}

	public void setTask_id(int task_id)
	{
		this.task = task_id;
	}

	public void setParent_id(String parent_id)
	{
		this.parent_id = parent_id;
	}

	public void setDate_started(Date date_started)
	{
		this.date_started = date_started.getTime();
	}

	public void setDate_ended(Date date_ended)
	{
		this.date_ended = date_ended.getTime();
	}

	public void setDate_created(Date date_created)
	{
		this.date_created = date_created.getTime();
	}

	public void setDate_modified(Date date_modified)
	{
		this.date_modified = date_modified.getTime();
	}

	public void setStaff_id(int staff_id)
	{
		this.staff = staff_id;
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

	public Date getDate_created()
	{
		return new Date(date_created * 1000);
	}

	public Date getDate_started()
	{
		return new Date(date_started * 1000);
	}

	public Date getDate_ended()
	{
		return new Date(date_ended * 1000);
	}

	public Date getDate_modified()
	{
		return new Date(date_modified * 1000);
	}

	public int getStaff()
	{
		return staff;
	}

	public int getPriority()
	{
		return priority;
	}

	public int get_class()
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

	// public void setFrom(String from_addr, String from_name)
	// {
	// this.from_addr = from_addr;
	// this.from_name = from_name;
	//
	// }
	//
	//
	// public void setTo(String to_addr, String to_name)
	// {
	// this.to_addr = to_addr;
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

	public int getInvoice_id()
	{
		return invoice_id;
	}

	public void setInvoice_id(int invoice_id)
	{
		this.invoice_id = invoice_id;
	}

	public int getContract_period_id()
	{
		return contract_period_id;
	}

	public void setContract_period_id(int contract_period_id)
	{
		this.contract_period_id = contract_period_id;
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public String getParent_id()
	{
		return parent_id;
	}

	public String getThread_id()
	{
		return thread_id;
	}

	public int getAgainst_id()
	{
		return against_id;
	}

	public int getOwner_id()
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

	public long getDate_logged()
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

	public int getTime_allocation()
	{
		return time_allocation;
	}

	public int getRateId()
	{
		return rate;
	}

	public int getRate_charged()
	{
		return rate_charged;
	}

	public List<Tag> getTag()
	{
		return tag;
	}

}
