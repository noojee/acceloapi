package au.com.noojee.acceloapi.dao;

import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
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
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("company_id", companyId).and(new Eq("contact_id", contactId)));
		List<Affiliation> affiliations = getByFilter(api, filter);

		return affiliations;
	}

	public List<Affiliation> getAffilicationsByPhone(AcceloApi api, String phone) throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("phone", phone));
		List<Affiliation> affiliations = getByFilter(api, filter);

		return affiliations;
	}

	public List<Affiliation> getAffilicationsForContact(AcceloApi api, Contact contact) throws AcceloException
	{
		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("contact", contact.getId()));
		List<Affiliation> affiliations = getByFilter(api, filter);

		return affiliations;
	}

	public List<Affiliation> getByActivity(AcceloApi acceloApi, Activity activity) throws AcceloException
	{

		AcceloFilter filter = new AcceloFilter();
		filter.where(new Eq("activity", activity.getId()));
		List<Affiliation> affiliations = getByFilter(acceloApi, filter);

		return affiliations;
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.affiliations;
	}

}
