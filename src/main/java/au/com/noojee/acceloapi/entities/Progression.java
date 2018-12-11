package au.com.noojee.acceloapi.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.entities.types.EntityStatus;

public class Progression extends AcceloEntity<Progression>
{


	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(Progression.class);


	protected String title;

	protected EntityStatus status;
	
	public Progression()
	{
		
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Progression [title=" + title + ", status=" + status + "]";
	} 


	

}
