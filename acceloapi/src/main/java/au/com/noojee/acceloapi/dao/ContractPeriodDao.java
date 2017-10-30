package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
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
					ResponseContactPeriods.class);
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
	protected Class<ContractPeriodDao.ResponseContactPeriods> getResponseListClass()
	{
		return ContractPeriodDao.ResponseContactPeriods.class;
	}

	
	public class ResponseContactPeriods   implements AcceloList<ContractPeriod>
	{
		Response response;

		@Override
		public List<ContractPeriod> getList()
		{
			return response.getList();
		}
		

	}
	
	class Response
	{
		List<ContractPeriod> periods;

		public List<ContractPeriod> getList()
		{
			return periods;
		}

	}


}
