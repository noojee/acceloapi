package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Priority;

public class PriorityDao extends AcceloDao<Priority>
{
	
	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.priority;
	}
	
	@Override
	protected Class<Priority> getEntityClass()
	{
		return Priority.class;
	}
	
	

	public class Response extends AcceloResponse<Priority>
	{
	}

	public class ResponseList extends AcceloResponseList<Priority>
	{
	}


	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}

	@Override
	protected Class<Response> getResponseClass()
	{
		return Response.class;
	}




}
