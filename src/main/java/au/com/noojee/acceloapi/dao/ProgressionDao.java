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
import au.com.noojee.acceloapi.entities.Progression;
import au.com.noojee.acceloapi.entities.Ticket;

public class ProgressionDao extends AcceloDao<Progression>
{
	static private Logger logger = LogManager.getLogger(ProgressionDao.class);

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
		return EndPoint.progressions;
	}

	public <E extends AcceloEntity<E>> List<Progression> getByEndPoint(EndPoint endPoint, E entity)
	{
		List<Progression> progressions = null;

		URL url;
		try
		{
			url = EndPoint.progressions.getURL(endPoint, (entity == null ? 0 : entity.getId())); // URL(endPoint.getURL()
																									// + "/" +
																									// entity.getId() +
																									// "/progressions");
			HTTPResponse result = AcceloApi.getInstance()._request(HTTPMethod.GET, url, null);

			if (result.getResponseCode() == 200)
			{
				ProgressionResponseList response = result.parseBody(ProgressionResponseList.class);
				progressions = response.progressions;
			}

		}
		catch (Throwable e)
		{
			logger.error(e, e);
			throw new AcceloException(e);
		}

		return progressions;
	}

	public <E extends AcceloEntity<E>> void triggerProgression(int progressionId, int entityId)
	{
		
		try
		{
			URL url = new URL(
					EndPoint.tickets.getURL() + "/" + entityId + "/progressions/" + progressionId + "/auto");
			HTTPResponse result = AcceloApi.getInstance()._request(HTTPMethod.POST, url, null);

			if (result.getResponseCode() == 200)
			{
				result.parseBody(ProgressionRunResponse.class);
			}
			else
				throw new AcceloException("Failed Progression: code: " + result.getResponseCode() + " Message: " + result.getResponseMessage());
		}
		catch (Throwable e)
		{
			logger.error(e, e);
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

	@Override
	protected Class<Progression> getEntityClass()
	{
		return Progression.class;
	}

	public class Response extends AcceloResponse<Progression>
	{
	}

	public class ResponseList extends AcceloResponseList<Progression>
	{
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ProgressionDao.ResponseList.class;
	}

	@Override
	protected Class<? extends AcceloResponse<Progression>> getResponseClass()
	{
		return ProgressionDao.Response.class;
	}

	public URL getEditURL(String domain, Progression ticket)
	{
		URL acceloEditURL = null;
		try
		{
			String action = "?action=edit_progression&id=" + ticket.getId();
			acceloEditURL = new URL("https", domain, 443, action);
		}
		catch (MalformedURLException e)
		{

		}
		return acceloEditURL;

	}

}
