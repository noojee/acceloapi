package au.com.noojee.acceloapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Request;

public class RequestDao extends AcceloDao<Request>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(Request.class);

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.request;
	}

	@Override
	protected Class<Request> getEntityClass()
	{
		return Request.class;
	}
	
	public class Response extends AcceloResponse<Request>
	{
	}

	public class ResponseList extends AcceloResponseList<Request>
	{
	}

	@Override
	protected Class<? extends AcceloResponseList<Request>> getResponseListClass()
	{
		return ResponseList.class;
	}
	
	@Override
	protected Class<? extends AcceloResponse<Request>> getResponseClass()
	{
		return Response.class;
	}




}
