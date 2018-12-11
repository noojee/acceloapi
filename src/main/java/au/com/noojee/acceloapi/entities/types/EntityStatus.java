package au.com.noojee.acceloapi.entities.types;

import au.com.noojee.acceloapi.entities.Ticket.Standing;

public class EntityStatus
{
	private int id; 						// A unique identifier for the status.
	private String title; 			// A title for the status.
	private String color; 			// The color the status will appear in the Accelo deployment.
	private Standing standing;	// The standing of the status. For example “active”, “paused”.
	private String start; 			// Either “yes” or “no”, whether this status is available upon creation of the object.
	int ordering;						// A number representing the status’ order on the Accelo deployment.
	
	

	public String getTitle()
	{
		return title;
	}
	
	public Standing getStanding()
	{
		return standing;
	}

	public int getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return "TicketStatus [id=" + id + ", title=" + title + ", color=" + color + ", standing=" + standing
				+ ", start=" + start + ", ordering=" + ordering + "]";
	}


	
	
}
