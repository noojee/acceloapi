package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.ContractPeriod;

public class ContractPeriodDao extends AcceloDao<ContractPeriod>
{

	public List<ContractPeriod> getByContract(Contract contract) throws AcceloException
	{

		List<ContractPeriod> periods;
		try
		{
			periods = AcceloApi.getInstance().getAll(EndPoint.contracts.getURL(contract.getId(), "/periods"), null, AcceloFieldList.ALL,
					ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return periods;

	}
	

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.contractPeriods;
	}

	
	@Override
	protected Class<ContractPeriod> getEntityClass()
	{
		return ContractPeriod.class;
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


	public class ResponseList extends AcceloResponseList<ContractPeriod>
	{
	}
	
	public class Response extends AcceloResponse<ContractPeriod>
	{
	}



}
