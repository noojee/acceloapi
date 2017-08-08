package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldValues;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.Company;
import au.com.noojee.acceloapi.Contact;
import au.com.noojee.acceloapi.AcceloApi.EndPoints;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;

public class Issue
{
	
    private Logger logger = LogManager.getLogger(Issue.class);

	public class Response extends AcceloResponse<Issue>
	{
	}

	public class ResponseList extends AcceloResponseList<Issue>
	{
	}

	private int id;
	private String title;
	private String custom_id;
	private String description;
	private String type;
	private String against;
	private int against_id;
	private String against_type;
	//private int contact_id;
	private String priority;
	private int resolution;
	private String status;
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
	private String company_name;
	private String object_budget;
	private String assignee;
	private int billable_seconds;
	private long date_last_interacted;
	private ArrayList<String> breadcrumbs;
	private String contract;
	private String resolution_detail;
	
	private Contact contact;
	private Company company;
	@SuppressWarnings("unused")
	private int classId;

	
	public static List<Issue> getIssues(AcceloApi acceloApi) throws AcceloException
	{

		AcceloResponseList<Issue> response = acceloApi.getAll(EndPoints.tickets, Issue.ResponseList.class);
		return response.getList();

	}
	
	   public void initCriticalIncident(Company company, Contact contact, String contactNo)
	    {
	        this.setTitle("Outof HoursCritical Incident");
	        this.setType("2"); // General Support
	        this.setContact(contact);
	        this.setCompany(company);
	        this.setStatus("2"); // Open
	        this.setClass(1); // General Support
	        this.setPriority("1"); // Critical, I think

	        String description = "An after hours Critical Incident has been lodged.\n"
	        + " The contact no. is " + contactNo;

	        this.setDescription(description);

	        // validate - contract

	    }


	private void setClass(int classId)
	{
		this.classId = classId;
		
	}

	public Issue insert(AcceloApi acceloApi) throws IOException, AcceloException
	{

		// if (company.getDefault_affiliation() == 0 ||
		// company.getDefault_affiliation() == -1)
		// throw new AcceloException("Default Contact for " +
		// this.getCompanyName() + " Not found.");

		// Assign the issue to the owning company.
		AcceloFieldValues arguments = new AcceloFieldValues();
		arguments.add("title", this.getTitle()); // URLEncoder.encode(issue.getTitle()));
		arguments.add("type_id", "2"); // General Support

		if (this.contact != null)
		{
			arguments.add("against_id", "" + contact.getid());
			arguments.add("against_type", "contact");
		}

		{
			arguments.add("against_id", "" + company.getId());
			arguments.add("against_type", "company");
		}
		// arguments.put("standing", "open"); // Is this what we want to do?
		arguments.add("status_id", "21"); // In Progress
		arguments.add("date_started", "" + (this.getDate_started().getTime() / 1000));
		arguments.add("date_entered", "" + (this.getDate_started().getTime() / 1000));
		arguments.add("description", this.getDescription());
		arguments.add("class_id", "42"); // Imported
		arguments.add("assignee", "2"); // assign to brett

        logger.error("Post args" + arguments);

		// byte[] args = AcceloApi.buildArgs(arguments);
		

		Issue.Response response = acceloApi.push(HTTPMethod.GET, EndPoints.tickets.getURL(), arguments, Issue.Response.class);
		if (response == null || response.getEntity() == null)
		{
			throw new AcceloException("Failed to insert case id:" + this.custom_id + " details:" + this.toString());
		}
		
		
		return response.getEntity();
		
	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getCustom_id()
	{
		return custom_id;
	}

	public String getDescription()
	{
		return description;
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

	public Contact getContact()
	{
		return contact;
	}

	public String getPriority()
	{
		return priority;
	}

	public int getResolution()
	{
		return resolution;
	}

	public String getStatus()
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

	public Date getDate_closed()
	{
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

	public String getCompanyName()
	{
		return company_name;
	}

	public String getObject_budget()
	{
		return object_budget;
	}

	public String getAssignee()
	{
		return assignee;
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
		return resolution_detail;
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

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public void setResolution(int resolution)
	{
		this.resolution = resolution;
	}

	public void setStatus(String status)
	{
		this.status = status;
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

	public void setCompanyName(String company)
	{
		this.company_name = company;
	}

	public void setCompany(Company company)
	{
		this.company = company;
	}

	public void setObject_budget(String object_budget)
	{
		this.object_budget = object_budget;
	}

	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
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
		return "Issue [id=" + id + ", title=" + title + ", custom_id=" + custom_id + ", description=" + description
				+ ", type=" + type + ", against=" + against + ", against_id=" + against_id + ", against_type="
				+ against_type + ", priority=" + priority + ", resolution=" + resolution + ", status=" + status
				+ ", standing=" + standing + ", submitted_by=" + submitted_by + ", date_submitted=" + date_submitted
				+ ", date_opened=" + date_opened + ", date_resolved=" + date_resolved + ", date_closed=" + date_closed
				+ ", date_started=" + date_started + ", date_due=" + date_due + ", closed_by=" + closed_by
				+ ", opened_by=" + opened_by + ", resolved_by=" + resolved_by + ", company_name=" + company_name
				+ ", object_budget=" + object_budget + ", assignee=" + assignee + ", billable_seconds="
				+ billable_seconds + ", date_last_interacted=" + date_last_interacted + ", breadcrumbs=" + breadcrumbs
				+ ", contract=" + contract + ", resolution_detail=" + resolution_detail + ", contact=" + contact
				+ ", company=" + company + "]";
	}

	

}
