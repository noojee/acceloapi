package au.com.noojee.acceloapi.entities;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.SearchFilterField;
import au.com.noojee.acceloapi.util.Constants;



public class Request extends AcceloEntity<Request>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(Request.class);

	@SearchFilterField
	private String title;
	private String body;
	@BasicFilterField
	private String standing;
	private String source;  // Only no value or "email" are accepted.
	@BasicFilterField
	private int lead_id; // The lead id of the request. If this is not provided, affiliation_id or affiliation details must be specified.
	private int thread_id;
	@DateFilterField
	private LocalDateTime date_created = Constants.DATETIMEZERO;
	@BasicFilterField
	private int type_id; // The type id of the request. Want to get a list of types? See the get request types endpoint.
	@BasicFilterField
	private int priority_id;
	@BasicFilterField
	private int claimer_id;
	@BasicFilterField
	private int affiliation_id; // The affiliation id of the request. If this is not provided, lead_id or affiliation details must be specified.
	
	
	
	public Request(Company company, String title, String body, int type_id, int affiliation_id)
	{
		this.title = title;
		this.body = body;
		this.type_id = type_id;
	}
	
	public String getStanding()
	{
		return standing;
	}

	public int getThreadId()
	{
		return thread_id;
	}

	public LocalDateTime getDateTimeCreated()
	{
		return date_created;
	}

	public int getPriorityId()
	{
		return priority_id;
	}

	public int getClaimerId()
	{
		return claimer_id;
	}

	public String getTitle()
	{
		return title;
	}
	public String getBody()
	{
		return body;
	}
	public int getTypeId()
	{
		return type_id;
	}
	public int getAffiliationId()
	{
		return affiliation_id;
	}
	public String getSource()
	{
		return source;
	}
	public int getLeadId()
	{
		return lead_id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void setStanding(String standing)
	{
		this.standing = standing;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public void setLeadId(int lead_id)
	{
		this.lead_id = lead_id;
	}

	public void setThreadId(int thread_id)
	{
		this.thread_id = thread_id;
	}

	public void setDateTimeCreated(LocalDateTime date_created)
	{
		this.date_created = date_created;
	}

	public void setTypeId(int type_id)
	{
		this.type_id = type_id;
	}

	public void setPriorityId(int priority_id)
	{
		this.priority_id = priority_id;
	}

	public void setClaimerId(int claimer_id)
	{
		this.claimer_id = claimer_id;
	}

	public void setAffiliationId(int affiliation_id)
	{
		this.affiliation_id = affiliation_id;
	}
}
			
			