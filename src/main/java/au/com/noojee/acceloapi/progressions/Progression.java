package au.com.noojee.acceloapi.progressions;

/**
 * 
 * @author bsutton
 *
 */
public class Progression
{
	private int id;
	
	private String title;
	
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
