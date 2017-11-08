package au.com.noojee.acceloapi.dao;

import java.util.List;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Affiliation;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.meta.Affiliation_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class AffiliationDao extends AcceloDao<Affiliation>
{


	public List<Affiliation> getAffilications(int companyId, int contactId) throws AcceloException
	{
		AcceloFilter<Affiliation> filter = new AcceloFilter<>();
		filter.where(filter.eq(Affiliation_.company, companyId).and(filter.eq(Affiliation_.contact, contactId)));
		List<Affiliation> affiliations = getByFilter(filter);

		return affiliations;
	}

	public List<Affiliation> getAffilicationsByPhone(String phone) throws AcceloException
	{
		AcceloFilter<Affiliation> filter = new AcceloFilter<>();
		filter.where(filter.eq(Affiliation_.contact_number, phone));
		List<Affiliation> affiliations = getByFilter(filter);

		return affiliations;
	}

	public List<Affiliation> getAffilicationsForContact(Contact contact) throws AcceloException
	{
		AcceloFilter<Affiliation> filter = new AcceloFilter<>();
		filter.where(filter.eq(Affiliation_.contact, contact.getId()));
		List<Affiliation> affiliations = getByFilter(filter);

		return affiliations;
	}
	

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.affiliations;
	}
	
	@Override
	protected Class<Affiliation> getEntityClass()
	{
		return Affiliation.class;
	}
	
	public class ResponseList extends AcceloResponseList<Affiliation>
	{
	}

	public class Response extends AcceloResponse<Affiliation>
	{
	}


	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}
	
	@Override
	protected Class<? extends AcceloResponse<Affiliation>> getResponseClass()
	{
		return AffiliationDao.Response.class;
	}



}
