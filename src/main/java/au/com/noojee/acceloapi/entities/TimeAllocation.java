package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.DateFilterField;

public class TimeAllocation extends AcceloEntity<TimeAllocation>
{
	
	@BasicFilterField
	private String standing;	// string	The standing of the logged time.
	private long billable;		// integer	The amount of billable time logged, in seconds.
	private long nonbillable;	// integer	The amount of nonbillable time logged, in seconds.
	private float charged;		// decimal	The rate charged for billable work.
	private String comments;	// string	Any comments made against the logged time.
	
	
	private String against_type;
	private int against_id;
	
	@DateFilterField
	private long date_locked;	// unix ts	The date the activity was locked, that is, when the logged time was approved for invoicing.
	@DateFilterField
	private long date_created;	// unix ts	The date the time was logged.
	
	
	public String getStanding()
	{
		return standing;
	}
	
	public long getBillable()
	{
		return billable;
	}
	
	public long getNonbillable()
	{
		return nonbillable;
	}
	
	public float getCharged()
	{
		return charged;
	}
	
	public String getComments()
	{
		return comments;
	}
	
	public LocalDate getDatelocked()
	{
		return toLocalDate(date_locked);
	}
	
	public LocalDate getDateCreated()
	{
		return toLocalDate(date_created);
	}

	public String getAgainst_type()
	{
		return against_type;
	}

	public void setAgainst_type(String against_type)
	{
		this.against_type = against_type;
	}

	public int getAgainst_id()
	{
		return against_id;
	}

	public void setAgainst_id(int against_id)
	{
		this.against_id = against_id;
	}
	
	

}
