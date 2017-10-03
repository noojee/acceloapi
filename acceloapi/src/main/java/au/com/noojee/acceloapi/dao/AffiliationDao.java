package au.com.noojee.acceloapi.dao;

import java.io.IOException;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Affiliation;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.Eq;

public class AffiliationDao extends AcceloDao<Affiliation, AffiliationDao.ResponseList>
{

	public class ResponseList extends AcceloResponseList<Affiliation>
	{
	}

	public List<Affiliation> getAffilications(AcceloApi api, int companyId, int contactId) throws AcceloException
	{
		AffiliationDao.ResponseList response;
		try
		{
			AcceloFilter filter = new AcceloFilter();
			filter.add(new Eq("company_id", companyId)).add(new Eq("contact_id", contactId));

			response = api.get(EndPoint.affiliations, filter, AcceloFieldList.ALL, AffiliationDao.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	
	public List<Affiliation> getAffilicationsByPhone(AcceloApi api, String phone) throws AcceloException
	{
		AffiliationDao.ResponseList response;
		try
		{
			AcceloFilter filter = new AcceloFilter();
			filter.add(new Eq("phone", phone));

			response = api.get(EndPoint.affiliations, filter, AcceloFieldList.ALL, AffiliationDao.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	public List<Affiliation> getAffilicationsForContact(AcceloApi api, Contact contact) throws AcceloException
	{
		AffiliationDao.ResponseList response;
		try
		{
			// String args = "_fields=_ALL,contact(_ALL)&filters=contact(" +
			// contact.getid() + ")";

			AcceloFilter filter = new AcceloFilter();
			filter.add(new Eq("contact", contact.getid()));

			response = api.get(EndPoint.affiliations, filter, AcceloFieldList.ALL, AffiliationDao.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	public List<Affiliation> getByActivity(AcceloApi acceloApi, Activity activity) throws AcceloException
	{
		AffiliationDao.ResponseList response;
		try
		{
			// String args = "_fields=_ALL,contact(_ALL)&filters=contact(" +
			// contact.getid() + ")";

			AcceloFilter filter = new AcceloFilter();
			filter.add(new Eq("activity", activity.getId()));

			response = acceloApi.get(EndPoint.affiliations, filter, AcceloFieldList.ALL,
					AffiliationDao.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}


	@Override
	public Affiliation getById(AcceloApi acceloApi, int id) throws AcceloException
	{
		return super.getById(acceloApi, EndPoint.affiliations, id);
	}

}
