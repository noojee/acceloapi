package au.com.noojee.acceloapi.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;

import com.google.gson.annotations.SerializedName;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.DateFilterField;
import au.com.noojee.acceloapi.entities.generator.MetaBasicFilterFields;
import au.com.noojee.acceloapi.util.Conversions;
import au.com.noojee.acceloapi.util.Formatters;

public class Activity extends AcceloEntity<Activity>
{

	@SuppressWarnings("unused")
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
	
	public enum Standing
	{
		unapproved, approved, invoiced, locked, empty;
	}
	

	private String subject;
	
	@BasicFilterField
	private String parent;		// the parent entity of the form "activities/0"

	private String thread;
	
	private String against;		// of the form staff/10

	@BasicFilterField
	private String against_type;

	@BasicFilterField
	private int against_id;
	
	private String owner; // of the form staff/2

	@BasicFilterField
	private int owner_id;

	@BasicFilterField
	private String owner_type;

	private String body;

	@BasicFilterField
	private Visibility visibility;

	private String details; // For meetings this is the location, for postals this is the address and for calls this is
							// the number.

	private Standing standing; // The standing of the activity, may be one of “unapproved”, “approved”, “invoiced”,
								// “locked”, or empty.
	private int invoice_id;
	private int contract_period_id;

	@DateFilterField
	private long date_created;
	@DateFilterField
	private long date_started;
	@DateFilterField
	private long date_ended;
	@DateFilterField
	private long date_logged;
	@DateFilterField
	private long date_modified;

	private long billable; // amount of billable time logged for the activity in
							// seconds.
	private long nonbillable; // amount of non-billable seconds.

	@BasicFilterField
	private int staff;

	@BasicFilterField
	private int priority;

	@BasicFilterField(name="class")
	@SerializedName("class")
	private int _class;

	@BasicFilterField
	private int task;

	@BasicFilterField
	private int time_allocation; // The id of the time allocation object for the activity.
	
	private int rate; // id of the rate object
	private int rate_charged; // The rate at which the billable time was charged.

	List<Tag> tag; // A list of tags associated with the activity
	
	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient String parent_id; 
		
		@BasicFilterField
		private String thread_id;

		
	}

	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void setAgainst(String against)
	{
		this.against = against;
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

	
	public void setVisiblity(Visibility visiblity)
	{
		this.visibility = visiblity;
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

	public void setTaskId(int taskId)
	{
		this.task = taskId;
	}

	
	public void setDateStarted(LocalDate dateStarted)
	{
		this.date_started = Conversions.toLong(dateStarted);
	}

	public void setDateEnded(LocalDate dateEnded)
	{
		this.date_ended = Conversions.toLong(dateEnded);
	}

	public void setDateCreated(LocalDate dateCreated)
	{
		this.date_created = Conversions.toLong(dateCreated);
	}

	public void setDateModified(LocalDate dateModified)
	{
		this.date_modified = Conversions.toLong(dateModified);
	}

	public void setStaffId(int staffId)
	{
		this.staff = staffId;
	}


	public String getSubject()
	{
		return subject;
	}

	public String getParent()
	{
		return parent;
	}

	
	public String getAgainstType()
	{
		return against_type;
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
		return Conversions.toLocalDate(date_created);
	}

	public LocalDate getDateStarted()
	{
		return Conversions.toLocalDate(date_started);
	}

	public LocalDate getDateEnded()
	{
		return Conversions.toLocalDate(date_ended);
	}

	public LocalDate getDateModified()
	{
		return Conversions.toLocalDate(date_modified);
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

	public Standing getStanding()
	{
		return standing;
	}

	public void setStanding(Standing standing)
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
		return visibility;
	}

	public long getDateLogged()
	{
		return date_logged;
	}

	public Duration getBillable()
	{
		return Duration.ofSeconds(billable);
	}

	public Duration getNonBillable()
	{
		return Duration.ofSeconds(nonbillable);
	}

	public void setBillable(Duration duration)
	{
		this.billable = duration.getSeconds();
	}

	public void setNonBillable(Duration duration)
	{
		nonbillable = duration.getSeconds();
	}

	public int getTimeAllocationId()
	{
		return time_allocation;
	}
	

	public void setTimeAllocationId(int timeAllocationId)
	{
		time_allocation = timeAllocationId;
	}


	public int getRateId()
	{
		return rate;
	}

	public Money getRateCharged()
	{
		return Money.of(rate_charged, Formatters.getCurrency());
	}

	public List<Tag> getTag()
	{
		return tag;
	}

	public boolean isApproved()
	{
		return standing != null && (standing.equals(Standing.approved) || standing.equals(Standing.invoiced));
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getAgainst()
	{
		return this.against;
	}

	@Override
	public int compareTo(Activity o)
	{
		return this.getDateCreated().compareTo(o.getDateCreated());
	}

	@Override
	public String toString()
	{
		return "Activity [id=" + getId() + ", subject=" + subject + ", parent=" + parent 
				+ ", thread=" + thread + ", against_type=" + against_type + ", against_id="
				+ against_id + ", owner_id=" + owner_id + ", owner_type=" + owner_type 
				+ ", body=" + body + ", visibility=" + visibility + ", details=" + details + ", date_created="
				+ date_created + ", date_started=" + date_started + ", date_ended=" + date_ended + ", date_logged="
				+ date_logged + ", date_modified=" + date_modified + ", billable=" + billable + ", nonbillable="
				+ nonbillable + ", staff=" + staff + ", priority=" + priority + ", _class=" + _class + ", task=" + task
				+ ", time_allocation=" + time_allocation + ", rate=" + rate + ", rate_charged=" + rate_charged
				+ ", tag=" + tag + "]";
	}

	
}
