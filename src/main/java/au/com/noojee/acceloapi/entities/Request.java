package au.com.noojee.acceloapi.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Request extends AcceloEntity<Request>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(Request.class);

	private int id;
	private String title;
	private String body;
	private int type_id; // The type id of the request. Want to get a list of types? See the get request types endpoint.
	private int affiliation_id; // The affiliation id of the request. If this is not provided, lead_id or affiliation details must be specified.
	private String source;  // Only no value or "email" are accepted.
	private int lead_id; // The lead id of the request. If this is not provided, affiliation_id or affiliation details must be specified.
	
	
	
	public Request(Company company, String title, String body, int type_id)
	{
		this.title = title;
		this.body = body;
		this.type_id = type_id;
		this.affiliation_id = company.getDefault_affiliation();
	}
	
	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}
	public String getBody()
	{
		return body;
	}
	public int getType_id()
	{
		return type_id;
	}
	public int getAffiliation_id()
	{
		return affiliation_id;
	}
	public String getSource()
	{
		return source;
	}
	public int getLead_id()
	{
		return lead_id;
	}
}
			
			