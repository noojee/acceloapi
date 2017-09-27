package au.com.noojee.acceloapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.noojee.acceloapi.entities.Company;

@SuppressWarnings("unused")
public class Request
{
	public class Response extends AcceloResponse<Request>
	{

		public String getStatusMessage()
		{
			return super.getStatusMessage();
		}

		public boolean isOK()
		{
			return super.isOK();
		}
	}

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
	
	public Response insert(AcceloApi acceloApi) throws IOException, AcceloException
	{
	    AcceloFieldValues values = new AcceloFieldValues();

		values.add("title", this.getTitle());
		values.add("body", this.getBody()); 
		values.add("type_id", "" + type_id);
		values.add("affiliation_id", "" + affiliation_id);

		Request.Response response = acceloApi.push(AcceloApi.HTTPMethod.POST, AcceloApi.EndPoints.requests.getURL(), values, Request.Response.class);

		return response;

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
			
			