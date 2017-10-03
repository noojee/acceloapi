package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.dao.ContractPeriodDaoTest.Response;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.ContractPeriod;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ContractPeriodDao extends AcceloDao<ContractPeriod, ContractPeriodDao.ResponseContactPeriods>
{

	public List<ContractPeriod> getContractPeriods(AcceloApi acceloApi, Contract contract) throws AcceloException
	{

		List<ContractPeriod> periods;
		try
		{
			periods = acceloApi.getAll(EndPoint.contracts.getURL(contract.getId(), "/periods"), null, AcceloFieldList.ALL,
					ResponseContactPeriods.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return periods;

	}
	
//	public <L extends AcceloList<ContractPeriod>> List<ContractPeriod> getAll(AcceloApi acceloApi, URL url, AcceloFilter filterMap, AcceloFieldList fieldList, Class<L> responseClass) throws IOException, AcceloException
//	{
//		List<ContractPeriod> entities = new ArrayList<>();
//		boolean more = true;
//		int page = 0;
//		while (more)
//		{
//			L responseList = acceloApi.get(url, filterMap, fieldList, responseClass, page);
//
//			if (responseList != null)
//			{
//				List<ContractPeriod> entityList = responseList.getList();
//
//				// If we get less than a page we must now have everything.
//				if (entityList.size() < AcceloApi.PAGE_SIZE)
//					more = false;
//
//				for (ContractPeriod entity : entityList)
//				{
//					entities.add(entity);
//				}
//				page += 1;
//			}
//
//		}
//
//		return entities;
//	}
//
//
//
//	public List<ContractPeriod> getAll(AcceloApi acceloApi, URL url, AcceloFilter filterMap, AcceloFieldList fieldList) throws IOException, AcceloException
//	{
//		List<ContractPeriod> entities = new ArrayList<>();
//		boolean more = true;
//		int page = 0;
//		while (more)
//		{
//			ResponseContactPeriods responseList = acceloApi.get(url, filterMap, fieldList, ResponseContactPeriods.class, page);
//
//			if (responseList != null)
//			{
//				List<ContractPeriod> entityList = responseList.getList();
//
//				// If we get less than a page we must now have everything.
//				if (entityList.size() < AcceloApi.PAGE_SIZE)
//					more = false;
//
//				for (ContractPeriod entity : entityList)
//				{
//					entities.add(entity);
//				}
//				page += 1;
//			}
//
//		}
//
//		return entities;
//	}
//


	
	@Override
	public ContractPeriod getById(AcceloApi acceloApi, int id) throws AcceloException
	{
		
		
		return super.getById(acceloApi,EndPoint.contractPeriods, id);
	}
	
	
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

//	public class ResponseContactPeriods extends AcceloResponse<List<ContractPeriod>> implements AcceloList<ContractPeriod>
//	{
//
//		List<ContractPeriod> periods;
//
//		public List<ContractPeriod> getList()
//		{
//			return periods;
//		}
//
//	}
//
//
//	public class ResponseList extends AcceloResponseList<ContractPeriod>
//	{
//	}



}
