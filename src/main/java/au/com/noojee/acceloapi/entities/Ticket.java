package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.annotations.SerializedName;

import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.MetaBasicFilterFields;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.OrderByField;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.entities.types.TicketStatus;
import au.com.noojee.acceloapi.util.LocalDateTimeHelper;

public class Ticket extends AcceloEntity<Ticket>
{
	static Logger logger = LogManager.getLogger();

	// private Logger logger = LogManager.getLogger(Ticket.class);
	static public String PRIORITY_CRITICAL = "1";

	@SuppressWarnings("unused")
	private class Meta implements MetaBasicFilterFields
	{
		@BasicFilterField
		private transient String contact_number; // filters over phone, fax and mobile
	}

	public enum Standing
	{
		submitted, open, resolved, closed, inactive;
	}

	@OrderByField
	private String title;

	@BasicFilterField
	private String custom_id;
	private String description;
	@BasicFilterField
	@SerializedName(value = "type_id", alternate = "issue_type") // insert and update use type_id.
	private int issue_type;

	@BasicFilterField
	private int affiliation; // The affiliated with this ticket which links
								// through to the contact
	private AgainstType against_type;
	private int against_id;

	private int company; // If against_type is company, then this holds the id of the company the ticket is
							// against.
	@BasicFilterField
	private Priority.NoojeePriority priority;

	@BasicFilterField(name = "class")
	@SerializedName("class")
	private int _class;

	@BasicFilterField
	private int resolution;

	@OrderByField
	@BasicFilterField
	private TicketStatus status; // Breaks our rules of using Ids but there is no
	// other way to get the status.

	@BasicFilterField
	private String referrer_type;
	@BasicFilterField
	private int referrer_id;

	@OrderByField
	@BasicFilterField
	private Standing standing;
	@BasicFilterField
	private int submitted_by;

	@OrderByField
	@DateFilterField
	private LocalDateTime date_submitted ;

	@OrderByField
	@DateFilterField
	private LocalDateTime date_opened ;

	@OrderByField
	private LocalDateTime date_resolved ;

	@OrderByField
	@DateFilterField
	private LocalDateTime date_closed ;

	@OrderByField
	@DateFilterField
	private LocalDateTime date_started ;

	@OrderByField
	@DateFilterField
	private LocalDate date_due ;
	@BasicFilterField
	private int opened_by; // staff member
	@BasicFilterField
	private int closed_by; // staff member
	@BasicFilterField
	private int resolved_by; // staff member
	private String object_budget;
	@BasicFilterField
	private int assignee; // staff member

	/*
	 * This field is read-only. To bill time create an activity attached to this ticket.
	 */
	private int billable_seconds;

	@OrderByField
	private LocalDate date_last_interacted ;

	@BasicFilterField
	private int contract; // the contract id or 0 if this ticket is unassigned.
	private String resolution_detail;

	private int issue_object_budget;
	private int contact;

	private String staff_bookmarked;

	public int getAffiliation()
	{
		return affiliation;
	}

	/**
	 * Returns true of the ticket is currently open.
	 * 
	 * @return
	 */
	public boolean isOpen()
	{
		return LocalDateTimeHelper.isEmpty(getDateTimeClosed())
				|| getDateTimeClosed().isAfter(LocalDateTime.now()); // I don't think this is possible. but still.
	}

	public int getCompanyId()
	{
		return company;
	}

	public String getTitle()
	{
		return trim(title);
	}

	private String trim(String field)
	{
		return (field == null ? "" : field.trim());
	}

	public String getCustomId()
	{
		return custom_id;
	}

	public String getDescription()
	{
		return trim(description);
	}

	public int getIssueType()
	{
		return issue_type;
	}

	public int getAgainstId()
	{
		return against_id;
	}

	public AgainstType getAgainstType()
	{
		return against_type;
	}

	public Priority.NoojeePriority getPriority()
	{
		return priority;
	}

	public int getClassId()
	{
		return _class;
	}

	public int getResolution()
	{
		return resolution;
	}

	public TicketStatus getStatus()
	{
		return status;
	}

	public Standing getStanding()
	{
		return standing;
	}

	public int getSubmittedBy()
	{
		return submitted_by;
	}

	public LocalDateTime getDateTimeSubmitted()
	{
		return date_submitted;
	}

	public LocalDateTime getDateTimeOpened()
	{
		return date_opened;
	}

	public LocalDateTime getDateTimeResolved()
	{
		return date_resolved;
	}

	/**
	 * @return null if the ticket is still open.
	 */
	public LocalDateTime getDateTimeClosed()
	{
		return date_closed;
	}

	public LocalDateTime getDateTimeStarted()
	{
		return date_started;
	}

	public LocalDate getDateDue()
	{
		return date_due;
	}

	public int getClosedBy()
	{
		return closed_by;
	}

	public int getOpenedBy()
	{
		return opened_by;
	}

	public int getResolvedBy()
	{
		return resolved_by;
	}

	public String getObjectBudget()
	{
		return object_budget;
	}

	/**
	 * The staff id or -1 if no assigned engineer.
	 */
	public int getAssignee()
	{
		return assignee;
	}

	/**
	 * Set the staff member assigned to this ticket.
	 * 
	 * @param staffId
	 */
	public void setAssignee(int staffId)
	{
		this.assignee = staffId;
	}

	public LocalDate getDateLastInteracted()
	{
		return date_last_interacted;
	}

	public int getContractId()
	{
		return contract;
	}

	public String getResolutionDetail()
	{
		return trim(resolution_detail);
	}

	/**
	 * Is the ticket Attached to a contract.
	 * 
	 * @return
	 */
	public boolean isAttached()
	{
		return contract != 0;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setCustomId(String custom_id)
	{
		this.custom_id = custom_id;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setIssueType(int issueTypeId)
	{
		this.issue_type = issueTypeId;
	}

	public void setAgainst(AgainstType type, int against_id)
	{
		this.against_type = type;
		this.against_id = against_id;
	}

	public void setPriority(Priority.NoojeePriority priority)
	{
		this.priority = priority;
	}

	public void setClass(int class_id)
	{
		this._class = class_id;
	}

	public void setResolution(int resolution)
	{
		this.resolution = resolution;
	}

	public void setStanding(Standing standing)
	{
		this.standing = standing;
	}

	public void setSubmittedBy(int submitted_by)
	{
		this.submitted_by = submitted_by;
	}

	public void setDateSubmitted(LocalDateTime date_submitted)
	{
		this.date_submitted = date_submitted;
	}

	public void setDateOpened(LocalDateTime date_opened)
	{
		this.date_opened = date_opened;
	}

	public void setDateResolved(LocalDateTime date_resolved)
	{
		this.date_resolved = date_resolved;
	}

	public void setDateClosed(LocalDateTime date_closed)
	{
		this.date_closed = date_closed;
	}

	public void setDateStarted(LocalDateTime date_started)
	{
		this.date_started = date_started;
	}

	public void setDateDue(LocalDate date_due)
	{
		this.date_due = date_due;
	}

	public void setClosedBy(int staffId)
	{
		this.closed_by = staffId;
	}

	public void setOpenedBy(int staffId)
	{
		this.opened_by = staffId;
	}

	/**
	 * The staff member that resolved the ticket.
	 * 
	 * @param resolved_by
	 */
	public void setResolvedBy(int staffId)
	{
		this.resolved_by = staffId;
	}

	public void setCompanyId(int companyId)
	{
		this.company = companyId;
	}

	public void setObjectBudget(String object_budget)
	{
		this.object_budget = object_budget;
	}

	public void setDateLastInteracted(LocalDate date_last_interacted)
	{
		this.date_last_interacted = date_last_interacted;
	}

	public void setContractId(int contract)
	{
		this.contract = contract;
	}

	public void setResolutionDetail(String resolution_detail)
	{
		this.resolution_detail = resolution_detail;
	}

	public int getIssue_object_budget()
	{
		return issue_object_budget;
	}

	public void setIssue_object_budget(int issue_object_budget)
	{
		this.issue_object_budget = issue_object_budget;
	}

	public int getContact()
	{
		return contact;
	}

	public void setContact(int contact)
	{
		this.contact = contact;
	}

	public String getStaff_bookmarked()
	{
		return staff_bookmarked;
	}

	public void setStaff_bookmarked(String staff_bookmarked)
	{
		this.staff_bookmarked = staff_bookmarked;
	}

	@Override
	public String toString()
	{
		return "Ticket [id=" + getId() + ", title=" + title + ", custom_id=" + custom_id + ", type=" + issue_type
				+ ", against_id=" + against_id + ", against_type=" + against_type + ", priority=" + priority
				+ ", resolution=" + resolution + ", status=" + status + ", standing=" + standing + ", submitted_by="
				+ submitted_by + ", date_submitted=" + date_submitted + ", date_opened=" + date_opened
				+ ", date_resolved=" + date_resolved + ", date_closed=" + date_closed + ", date_started=" + date_started
				+ ", date_due=" + date_due + ", closed_by=" + closed_by + ", opened_by=" + opened_by + ", resolved_by="
				+ resolved_by + ", company=" + company + ", object_budget=" + object_budget + ", assignee=" + assignee
				+ ", billable_seconds=" + billable_seconds + ", date_last_interacted=" + date_last_interacted
				+ ", contract=" + contract + ", resolution_detail=" + resolution_detail
				+ ", description=" + description + "]";
	}

	@Override
	public int compareTo(Ticket o)
	{
		return this.getId() - o.getId();
	}

}
