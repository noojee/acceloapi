package au.com.noojee.acceloapi.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.money.Money;

import com.google.gson.annotations.SerializedName;

import au.com.noojee.acceloapi.dao.ActivityOwnerType;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.util.Conversions;

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
	

	private Medium medium = Medium.call; // The type of activity.

	private String subject;

	@BasicFilterField
	private String parent; // the parent entity of the form "activities/0"

	private String thread;

	@BasicFilterField
	private AgainstType against_type;

	@BasicFilterField
	private int against_id;

	@BasicFilterField
	private int owner_id;

	@BasicFilterField
	private ActivityOwnerType owner_type;

	private String body;

	@BasicFilterField
	private Visibility visibility = Visibility.all;

	private String details; // For meetings this is the location, for postals this is the address and for calls this is
							// the number.

	private Standing standing; // The standing of the activity, may be one of “unapproved”, “approved”, “invoiced”,
								// “locked”, or empty.
	private int invoice_id;
	private int contract_period_id;

	@DateFilterField
	private LocalDateTime date_created;
	@DateFilterField
	private LocalDateTime date_started;
	@DateFilterField
	private LocalDateTime date_ended;
	@DateFilterField
	private LocalDateTime date_logged;
	@DateFilterField
	private LocalDateTime date_modified;

	private long billable; // amount of billable time logged for the activity in
							// seconds.
	private long nonbillable; // amount of non-billable seconds.

	@BasicFilterField
	private int staff;

	@BasicFilterField
	private int priority;

	@BasicFilterField(name = "class")
	@SerializedName("class")
	private int _class;

	@BasicFilterField
	private int task;

	@BasicFilterField
	private int time_allocation; // The id of the time allocation object for the activity.

	private int rate; // id of the rate object
	private int rate_charged; // The rate at which the billable time was charged.

	private int thread_id;

	private int parent_id;

	List<Tag> tag; // A list of tags associated with the activity

	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@SuppressWarnings("hiding")
		@BasicFilterField
		private transient String parent_id;

		@SuppressWarnings("hiding")
		@BasicFilterField
		private String thread_id;

	}

	public void setOwner(ActivityOwnerType ownerType, int ownerId)
	{
		this.owner_type = ownerType;
		this.owner_id = ownerId;

	}

	public void setMedium(Medium medium)
	{
		this.medium = medium;
	}

	public Medium getMedium()
	{
		return this.medium;
	}

	public void setSubject(String subject)

	{
		this.subject = subject;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void setAgainst(AgainstType type, int id)
	{
		this.against_type = type;
		this.against_id = id;
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

	public void setDateTimeStarted(LocalDateTime dateStarted)
	{
		this.date_started = dateStarted;
	}

	public void setDateTimeEnded(LocalDateTime dateEnded)
	{
		this.date_ended = dateEnded;
	}

	public String getSubject()
	{
		return subject;
	}

	public String getParent()
	{
		return parent;
	}

	public AgainstType getAgainstType()
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

	public Standing getStanding()
	{
		return standing;
	}

	public int getInvoiceId()
	{
		return invoice_id;
	}

	public int getContractPeriodId()
	{
		return contract_period_id;
	}

	public int getAgainstId()
	{
		return against_id;
	}

	public int getOwnerId()
	{
		return owner_id;
	}

	public ActivityOwnerType getOwnerType()
	{
		return owner_type;
	}

	public Visibility getVisiblity()
	{
		return visibility;
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

	public int getRateId()
	{
		return rate;
	}

	public Money getRateCharged()
	{
		return Conversions.asMoney(rate_charged);
	}

	public List<Tag> getTag()
	{
		return tag;
	}

	public boolean isApproved()
	{
		return standing != null && (standing.equals(Standing.approved) || standing.equals(Standing.invoiced));
	}

	public int getOwner_id()
	{
		return owner_id;
	}

	public ActivityOwnerType getOwner_type()
	{
		return owner_type;
	}

	public Visibility getVisibility()
	{
		return visibility;
	}

	public LocalDateTime getDateTimeCreated()
	{
		return date_created;
	}

	public void setDateTimeCreated(LocalDateTime dateTimeCreated)
	{
		
		this.date_created = dateTimeCreated;
	}
	
	public LocalDateTime getDateTimeStarted()
	{
		return date_started;
	}

	public LocalDateTime getDateTimeEnded()
	{
		return date_ended;
	}

	public LocalDateTime getDateTimeLogged()
	{
		return date_logged;
	}

	public LocalDateTime getDateTimeModified()
	{
		return date_modified;
	}

	public long getNonbillable()
	{
		return nonbillable;
	}

	public int getClazz()
	{
		return _class;
	}

	public int getTime_allocation()
	{
		return time_allocation;
	}

	public int getRate()
	{
		return rate;
	}

	public int getRate_charged()
	{
		return rate_charged;
	}

	public int getThreadId()
	{
		return thread_id;
	}

	public int getParentId()
	{
		return parent_id;
	}

	@Override
	public int compareTo(Activity o)
	{
		return this.getDateTimeCreated().compareTo(o.getDateTimeCreated());
	}

	@Override
	public String toString()
	{
		return "Activity [id=" + getId() + ", against_type=" + against_type + ", against_id="
				+ against_id + ", parent=" + parent
				+ ", thread=" + thread +  ", owner_id=" + owner_id + ", owner_type=" + owner_type
				+ ",\n visibility=" + visibility + ", details=" + details + ", date_created="
				+ date_created + ", date_started=" + date_started + ", date_ended=" + date_ended + ", date_logged="
				+ date_logged + ",\n date_modified=" + date_modified + ", billable=" + billable + ", nonbillable="
				+ nonbillable + ", staff=" + staff + ", priority=" + priority + ", _class=" + _class + ", task=" + task
				+ ", time_allocation=" + time_allocation + ", rate=" + rate + ", rate_charged=" + rate_charged
				+ ", tag=" + tag+ ",\n subject=" + subject  + ", body=" + body + "]\n\n";
	}

	public boolean isSystemActivity()
	{
		return getOwnerType() == ActivityOwnerType.staff && staff == Staff.SYSTEM;
	}

	public String getSummary()
	{
		String summary = this.body.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("  ", " ");
		summary = summary.substring(0, Math.min(150,  summary.length()));
		return summary;
	}
	

}
