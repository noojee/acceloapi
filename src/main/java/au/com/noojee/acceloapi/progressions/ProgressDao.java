package au.com.noojee.acceloapi.progressions;

import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.annotations.SerializedName;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.HTTPResponse;
import au.com.noojee.acceloapi.Meta;
import au.com.noojee.acceloapi.entities.Ticket;

public class ProgressDao
{
	static Logger logger = LogManager.getLogger();

	
	List<Progression> getByEndPoint(EndPoint endPoint, Ticket ticket)
	{
		List<Progression> progressions = null;
		
		URL url;
		try
		{
			url = new URL(endPoint.getURL() + "/" + ticket.getId() + "/progressions");
			HTTPResponse result = AcceloApi.getInstance()._request(HTTPMethod.GET, url, null);
			
			if (result.getResponseCode() == 200)
			{
				ProgressionResponseList response = result.parseBody(ProgressionResponseList.class);
				progressions = response.progressions;
				
				
			}

		}
		catch (Throwable e)
		{
			logger.error(e,e);
			throw new AcceloException(e);
		}
		
		return progressions;
	}
	
	void triggerProgression(Progression progression, Ticket ticket)
	{
		
		URL url;
		try
		{
			
			// Thanks for your note. If you have a Ticket/Issue, it will automatically be assigned to the current Period on the Retainer.

			
			url = new URL(EndPoint.tickets.getURL() + "/" + ticket.getId() + "/progressions/" + progression.getId() + "/auto");
			HTTPResponse result = AcceloApi.getInstance()._request(HTTPMethod.POST, url, null);
			
			if (result.getResponseCode() == 200)
			{
				ProgressionRunResponse response = result.parseBody(ProgressionRunResponse.class);
				
				
			}

		}
		catch (Throwable e)
		{
			logger.error(e,e);
			throw new AcceloException(e);
		}
		
	}
	
	class ProgressionRunResponse
	{
		Meta meta;
		
		@SerializedName("response")
		Ticket ticket;
	}
	
	class ProgressionResponseList
	{
		Meta meta;
		@SerializedName("response")
		List<Progression> progressions;
	}
	
}
