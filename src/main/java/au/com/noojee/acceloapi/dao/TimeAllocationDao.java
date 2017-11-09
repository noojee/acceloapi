package au.com.noojee.acceloapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.TimeAllocation;

public class TimeAllocationDao extends AcceloDao<TimeAllocation>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(TimeAllocation.class);


	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.activitiesAllocations;
	}

	@Override
	protected Class<TimeAllocation> getEntityClass()
	{
		return TimeAllocation.class;
	}
	
	public class Response extends AcceloResponse<TimeAllocation>
	{
	}

	public class ResponseList extends AcceloResponseList<TimeAllocation>
	{
	}

	@Override
	protected Class<? extends AcceloResponseList<TimeAllocation>> getResponseListClass()
	{
		return ResponseList.class;
	}
	
	@Override
	protected Class<? extends AcceloResponse<TimeAllocation>> getResponseClass()
	{
		return Response.class;
	}



}
