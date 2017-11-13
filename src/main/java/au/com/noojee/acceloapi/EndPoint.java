package au.com.noojee.acceloapi;

import java.net.MalformedURLException;
import java.net.URL;

public enum EndPoint
{
	activities("activities"), affiliations("affiliations"), companies("companies"), contacts("contacts"), contracts(
			"contracts"), tickets("issues"), requests("requests"), staff(
					"staff"), statuses("statuses"), invoices("invoices"), contractPeriods(
							"contracts/periods"), activitiesAllocations("activities/allocations"), request("request");

	private String endpoint;

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

}