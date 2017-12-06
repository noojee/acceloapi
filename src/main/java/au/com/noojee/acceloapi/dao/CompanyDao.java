package au.com.noojee.acceloapi.dao;

import java.util.List;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.meta.Company_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class CompanyDao extends AcceloDao<Company>
{

	public Company getByName(String companyName) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);
		fields.add(Contact.FIELDS_ALL);

		AcceloFilter<Company> filter = new AcceloFilter<>();
		filter.where(filter.search(companyName));

		List<Company> companies = getByFilter(filter, fields);

		return companies.size() > 0 ? companies.get(0) : null;
	}

	public Company getCompanyByContactId(int contractId) throws AcceloException
	{

		AcceloFilter<Company> filter = new AcceloFilter<>();
		filter.where(filter.eq(Company_.id, contractId));
		List<Company> companies = getByFilter(filter);

		Company company = null;
		company = companies.size() > 0 ? companies.get(0) : null;

		return company;
	}
	
	/*
	 * Is there an active contract for this company
	 */
	public boolean hasActiveContract(Company company)
	{
		return  new ContractDao().getActiveContract(company) != null;
	}


	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.companies;
	}
	
	@Override
	protected Class<Company> getEntityClass()
	{
		return Company.class;
	}

	
	public class ResponseList extends AcceloResponseList<Company>
	{

	}
	
	public class Response extends AcceloResponse<Company>
	{

	}

	@Override
	protected Class<CompanyDao.ResponseList> getResponseListClass()
	{
		return CompanyDao.ResponseList.class;
	}
	
	@Override
	protected Class<? extends AcceloResponse<Company>> getResponseClass()
	{
		return CompanyDao.Response.class;
	}


}
