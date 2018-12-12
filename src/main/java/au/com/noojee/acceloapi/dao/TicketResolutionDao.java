package au.com.noojee.acceloapi.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.annotations.SerializedName;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.HTTPResponse;
import au.com.noojee.acceloapi.Meta;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.TicketResolution;

public class TicketResolutionDao extends AcceloDao<TicketResolution>
{
	static private Logger logger = LogManager.getLogger(TicketResolutionDao.class);

	@Override
	protected AcceloFieldList getFieldList()
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");
		fields.add("status._ALL");
		return fields;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.resolutions;
	}
	
	
		
	
	class TicketResolutionRunResponse
	{
		Meta meta;
		
		@SerializedName("response")
		Ticket ticket;
	}
	
	class TicketResolutionResponseList
	{
		Meta meta;
		@SerializedName("response")
		List<TicketResolution> progressions;
	}


	@Override
	protected Class<TicketResolution> getEntityClass()
	{
		return TicketResolution.class;
	}

	public class Response extends AcceloResponse<TicketResolution>
	{
	}

	public class ResponseList extends AcceloResponseList<TicketResolution>
	{
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return TicketResolutionDao.ResponseList.class;
	}

	@Override
	protected Class<? extends AcceloResponse<TicketResolution>> getResponseClass()
	{
		return TicketResolutionDao.Response.class;
	}

	public URL getEditURL(String domain, TicketResolution ticket)
	{
		URL acceloEditURL = null;
		try
		{
			String action = "?action=edit_support_resolution&id=" + ticket.getId();
			acceloEditURL = new URL("https", domain, 443, action);
		}
		catch (MalformedURLException e)
		{

		}
		return acceloEditURL;

	}

}
