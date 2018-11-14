package au.com.noojee.acceloapi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.TimeAllocation;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class TimeAllocationDao extends AcceloDao<TimeAllocation>
{
	@SuppressWarnings("unused")
	static private Logger logger = LogManager.getLogger(TimeAllocation.class);


	@Override
	public List<TimeAllocation> getByFilter(AcceloFilter<TimeAllocation> filter) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add("_ALL");

		Optional<TimeAllocation> allocation = this.getSingleByFilter(filter, fields);
		
		List<TimeAllocation> allocations = new ArrayList<>();
		allocation.ifPresent(a -> allocations.add(a));
		
		return allocations;
	}

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

	/**
	 * Even though the doco states this is 
	 * @author bsutton
	 *
	 */
	
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
