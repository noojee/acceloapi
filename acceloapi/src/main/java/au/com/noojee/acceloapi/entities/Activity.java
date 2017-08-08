package au.com.noojee.acceloapi.entities;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloApi.EndPoints;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;

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
	private String body;
	private int against_id;
	private String against_type;
	private int owner_id;
	private String owner_type;

	private Medium medium;
	private Visibility visiblity;

	private String details; // For meetings this is the location, for postals
	// this is the address and for calls this is the
	// number.

	private int priority_id;
	private int _class_id;
	private String thread_id;
	private int task_id;
	private String parent_id;

	private long date_started;
	private long date_due;
	private long date_ended;

	private long date_created;
	private long date_updated;

	private int staff_id;
//	private String from_addr;
//	private String from_name;
//	private String to_addr;
//	private String to_name;
//	
	
	
	public void insert(AcceloApi acceloApi) throws IOException, AcceloException
	{
		AcceloFieldValues values = marshallArgs();

		Issue.Response response = acceloApi.push(HTTPMethod.POST, EndPoints.activities.getURL(), values, Issue.Response.class);
		logger.debug(response);
	}


	private AcceloFieldValues marshallArgs()
	{
		AcceloFieldValues args = new AcceloFieldValues();

		args.add("subject", subject);
		args.add("body", body);
		args.add("against_id", ""+ against_id);
		args.add("against_type", against_type);
		args.add("owner_id", "" + owner_id);
		args.add("owner_type", owner_type);
		args.add("medium", medium.toString());
		args.add("visibility", visiblity.toString());
		args.add("details", details);
		//args.put("priority_id", "" + priority_id);
		//args.put("class_id", "" + _class_id);
		//args.put("thread_id", "" + thread_id);
		//args.put("task_id", "" + task_id);
		//args.put("parent_id", "" + against_id);
		args.add("date_started", "" + date_started);
		//args.put("date_ended", "" + date_ended);
		args.add("date_created", "" + date_created);
		//args.put("staff_id", "" + staff_id);

		return args;
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


	public void setPriority_id(int priority_id)
	{
		this.priority_id = priority_id;
	}


	public void set_class_id(int _class_id)
	{
		this._class_id = _class_id;
	}


	public void setThread_id(String thread_id)
	{
		this.thread_id = thread_id;
	}


	public void setTask_id(int task_id)
	{
		this.task_id = task_id;
	}


	public void setParent_id(String parent_id)
	{
		this.parent_id = parent_id;
	}


	public void setDate_started(long date_started)
	{
		this.date_started = date_started;
	}


	public void setDate_due(long date_due)
	{
		this.date_due = date_due;
	}


	public void setDate_ended(long date_ended)
	{
		this.date_ended = date_ended;
	}


	public void setDate_created(long date_created)
	{
		this.date_created = date_created;
	}


	public void setDate_updated(long date_updated)
	{
		this.date_updated = date_updated;
	}


	public void setStaff_id(int staff_id)
	{
		this.staff_id = staff_id;
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

	public long getDate_created()
	{
		return date_created;
	}

	public long getDate_started()
	{
		return date_started;
	}

	public long getDate_ended()
	{
		return date_ended;
	}

	public long getDate_due()
	{
		return date_due;
	}

	public long getDate_updated()
	{
		return date_updated;
	}

	public int getStaff()
	{
		return staff_id;
	}

	public int getPriority()
	{
		return priority_id;
	}

	public int get_class()
	{
		return _class_id;
	}

	public int getTask()
	{
		return task_id;
	}


//	public void setFrom(String from_addr, String from_name)
//	{
//		this.from_addr = from_addr;
//		this.from_name = from_name;
//		
//	}
//
//
//	public void setTo(String to_addr, String to_name)
//	{
//		this.to_addr = to_addr;
//		this.to_name = to_name;
//	}
//


}
