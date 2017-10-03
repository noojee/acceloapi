package au.com.noojee.acceloapi.dao;

import java.io.IOException;
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

public class ContactDao extends AcceloDao<Contact, ContactDao.ResponseList>
{
	

	public Contact getContact(AcceloApi acceloApi, String contact_firstname, String contact_lastname)
			throws AcceloException
	{
		ContactDao.ResponseList response = null;
		try
		{
			if (contact_firstname != null && contact_lastname != null)
			{
				AcceloFilter filters = new AcceloFilter();
				filters.add(new Search(contact_firstname + " " + contact_lastname));

				response = acceloApi.get(EndPoint.contacts, filters, AcceloFieldList.ALL, ContactDao.ResponseList.class);
			}
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		Contact contact = null;
		if (response != null)
			contact = response.getList().size() > 0 ? response.getList().get(0) : null;

		return contact;
	}

	public List<Contact> getByPhone(AcceloApi acceloApi, String phone) throws AcceloException
	{
		AcceloFieldList fields = new AcceloFieldList();
		fields.add(AcceloFieldList._ALL);
		fields.add(Company.FIELDS_ALL);

		AcceloFilter filters = new AcceloFilter();
		filters.add(new Eq("contact_number", phone));

		ContactDao.ResponseList request;
		try
		{
			request = acceloApi.get(EndPoint.contacts, filters, fields, ContactDao.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return request.getList();

	}

	public class ResponseList extends AcceloResponseList<Contact>
	{
	}

	@Override
	protected Class<ContactDao.ResponseList> getResponseListClass()
	{
		return ContactDao.ResponseList.class;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.contacts;
	}


}
