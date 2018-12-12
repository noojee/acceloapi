package au.com.noojee.acceloapi.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class TicketResolution extends AcceloEntity<TicketResolution>
{

	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(TicketResolution.class);

	protected String title;

	protected int parent;

	String description;

	String report;

	Standing standing;

	public String getTitle()
	{
		return title;
	}

	enum Standing
	{
		active, inactive
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "TicketResolution [title=" + title + ", parent=" + parent + ", description=" + description + ", report="
				+ report + ", standing=" + standing + "]";
	};


}
