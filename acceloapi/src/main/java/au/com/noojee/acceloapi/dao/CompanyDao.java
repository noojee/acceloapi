package au.com.noojee.acceloapi.dao;

import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;
import au.com.noojee.acceloapi.filter.expressions.Search;

public class CompanyDao extends AcceloDao<Company, CompanyDao.ResponseList>
{
	public class ResponseList extends AcceloResponseList<Company>
	{

	}

	public Company getByName(AcceloApi api, String companyName) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);
		fields.add(Contact.FIELDS_ALL);

		AcceloFilter filter = new AcceloFilter();
		filter.where(new Search(companyName));

		List<Company> companies = getByFilter(api, filter, fields);

		Company company = null;
		company = companies.size() > 0 ? companies.get(0) : null;

		return company;
	}

	public Company getCompanyByContactId(AcceloApi api, String contractId) throws AcceloException
	{

		AcceloFilter filters = new AcceloFilter();
		filters.where(new Eq("id", contractId));
		List<Company> companies = getByFilter(api, filters);

		Company company = null;
		company = companies.size() > 0 ? companies.get(0) : null;

		return company;
	}

	// public static List<Company> getList(AcceloApi acceloApi) throws
	// AcceloException
	// {
	// Company.Response request;
	// try
	// {
	// request = acceloApi.get(EndPoint.companies, null, AcceloFieldList.ALL,
	// Company.Response.class);
	// }
	// catch (IOException e)
	// {
	// throw new AcceloException(e);
	// }
	//
	// return request.getList();
	//
	// }

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.companies;
	}

	@Override
	protected Class<CompanyDao.ResponseList> getResponseListClass()
	{
		return CompanyDao.ResponseList.class;
	}

}
