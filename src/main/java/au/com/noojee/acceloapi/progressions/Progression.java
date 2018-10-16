package au.com.noojee.acceloapi.progressions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * 
 * @author bsutton
 *
 */
public class Progression
{
	private int id;
	
	@SuppressFBWarnings
	private String title;
	
	@SuppressFBWarnings
	private Status status;
	
	
	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public Status getStatus()
	{
		return status;
	}

	@Override
	public String toString()
	{
		return "Progression [id=" + id + ", title=" + title + ", status=" + status + "]";
	}
}
