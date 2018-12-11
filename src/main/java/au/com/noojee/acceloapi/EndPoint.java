package au.com.noojee.acceloapi;

import java.net.MalformedURLException;
import java.net.URL;

public enum EndPoint
{
	activities("activities")
	, affiliations("affiliations")
	, companies("companies")
	, contacts("contacts")
	, contracts("contracts")
	, tickets("issues")
	, requests("requests")
	, staff("staff"), statuses("statuses")
	, invoices("invoices")
	, contractPeriods("contracts/periods")
	, activitiesAllocations("activities/allocations")
	, request("request")
	, progressions("progressions")
	;

	protected String endpoint;

	EndPoint(String endpoint)
	{
		this.endpoint = endpoint;
	}


	
	@Override
	public String toString()
	{
		return this.endpoint;
	}

	public URL getURL() throws MalformedURLException
	{
		return new URL(AcceloApi.getInstance().getBaseURL() + this.endpoint);
	}

	public URL getURL(int id) throws MalformedURLException
	{
		return new URL(AcceloApi.getInstance().getBaseURL() + this.endpoint + "/" + id);
	}

	public URL getURL(String args) throws MalformedURLException
	{
		return new URL(AcceloApi.getInstance().getBaseURL() + this.endpoint + "?" + args);
	}

	public URL getURL(int id, String path) throws MalformedURLException
	{
		return new URL(AcceloApi.getInstance().getBaseURL() + this.endpoint + "/" + id + path);
	}

	// currently this method is only use fo rthe progression end point.
	public URL getURL(EndPoint resource, int id) throws MalformedURLException
	{
		return new URL(AcceloApi.getInstance().getBaseURL() + resource.endpoint + "/" + id + "/" + this.endpoint);
	}
	

	/**
	 * trigger a progression.
	 * 
	 * @param resource the entity to trigger a progression on (e.g. Issue)
	 * @param id the id of the entity the progression is to be triggered on.
	 * @param progression_id the progression to be triggered.
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getURL(EndPoint resource, int id, int progression_id) throws MalformedURLException
	{
		return new URL(AcceloApi.getInstance().getBaseURL() + resource.endpoint + "/" + id + "/" + this.endpoint + "/"
				+ progression_id + "/auto");
	}

}