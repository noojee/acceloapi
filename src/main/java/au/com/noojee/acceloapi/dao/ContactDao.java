package au.com.noojee.acceloapi.dao;

import java.util.List;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Affiliation;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.meta.Contact_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ContactDao extends AcceloDao<Contact>
{

	public Contact getContact(String contact_firstname, String contact_lastname)
			throws AcceloException
	{
		Contact contact = null;

		if (contact_firstname != null && contact_lastname != null)
		{
			AcceloFilter<Contact> filter = new AcceloFilter<>();
			filter.where(filter.search(contact_firstname + " " + contact_lastname));
			List<Contact> contacts = getByFilter(filter);
			contact = contacts.size() > 0 ? contacts.get(0) : null;
		}

		return contact;
	}

	public List<Contact> getByPhone(String phone) throws AcceloException
	{
		AcceloFilter<Contact> filters = new AcceloFilter<>();
		filters.where(filters.eq(Contact_.contact_number, phone));

		List<Contact> contacts = getByFilter(filters);

		return contacts;

	}

	@Override
	protected AcceloFieldList getFieldList()
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);
		//fields.add(Company.FIELDS_ALL);
		return fields;
	}

	/**
	 * Gets the default company the contact is attached to.
	 * 
	 * @param contact
	 * @return
	 */
	public Company getDefaultCompany(Contact contact)
	{
		Affiliation affiliation = new AffiliationDao().getById(contact.getDefaultAffiliation());

		return new CompanyDao().getById(affiliation.getCompanyId());
	}

	/**
	 * Get the list of contacts attached to this company
	 * 
	 * @param id
	 */
	public List<Contact> getByCompany(int companyId)
	{
		AcceloFilter<Contact> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType.company, companyId));

		return super.getByFilter(filter);
	}

	
	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.contacts;
	}

	@Override
	protected Class<Contact> getEntityClass()
	{
		return Contact.class;
	}
	
	public class ResponseList extends AcceloResponseList<Contact>
	{
	}

	public class Response extends AcceloResponse<Contact>
	{
	}

	@Override
	protected Class<ContactDao.ResponseList> getResponseListClass()
	{
		return ContactDao.ResponseList.class;
	}

	@Override
	protected Class<? extends AcceloResponse<Contact>> getResponseClass()
	{
		return ContactDao.Response.class;
	}


}
