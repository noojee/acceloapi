package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloApi.EndPoints;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFilter;
import au.com.noojee.acceloapi.AcceloFilter.SimpleMatch;
import au.com.noojee.acceloapi.AcceloResponseList;

public class Affiliation
{
	private int id;

	private String company;
	private int postal_address; // id of an address
	private int physical_address; // id of an address
	private String phone; // phone no.
	private int contact; // contact id
	private String mobile; // mobile no.
	private String email; // email address
	private String position; // ??
	private long date_last_interacted; // unix time
	private long date_modified; // unix time
	private int staff_bookmarked;
	private String standing;

	private static HashMap<Integer, Affiliation> affiliationCache = new HashMap<>();

	public class Response extends AcceloResponseList<Affiliation>
	{
	}

	public static List<Affiliation> getAffilications(AcceloApi api, int companyId, int contactId) throws AcceloException
	{
		Affiliation.Response response;
		try
		{
			// String args = "_filters=company_id(" + companyId +
			// "),contact_id(" + contactId + ")&_fields=_ALL";

			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("company_id", companyId)).add(new SimpleMatch("contact_id", contactId));

			response = api.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter, AcceloFieldList.ALL,
					Affiliation.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	public static Affiliation getById(AcceloApi acceloApi, int affiliationId) throws AcceloException
	{
		Affiliation affiliation = affiliationCache.get(affiliationId);

		if (affiliation == null)
		{
			AcceloFieldList fields = new AcceloFieldList();
			fields.add(AcceloFieldList._ALL);

			AcceloFilter filters = new AcceloFilter();
			filters.add(new AcceloFilter.SimpleMatch("id", affiliationId));

			Affiliation.Response response;
			try
			{
				response = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.affiliations.getURL(), filters,
						fields, Affiliation.Response.class);
			}
			catch (IOException e)
			{
				throw new AcceloException(e);
			}

			if (response != null)
				affiliation = response.getList().size() > 0 ? response.getList().get(0) : null;
			affiliationCache.put(affiliationId, affiliation);
		}

		return affiliation;
	}

	public static List<Affiliation> getAffilicationsByPhone(AcceloApi api, String phone) throws AcceloException
	{
		Affiliation.Response response;
		try
		{
			// String args = "_fields=_ALL,contact(_ALL)&filters=phone(" + phone
			// + ")";
			// String args = "_filters=phone(" + phone + ")";

			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("phone", phone));

			response = api.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter, AcceloFieldList.ALL,
					Affiliation.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	public static List<Affiliation> getAffilicationsForContact(AcceloApi api, Contact contact) throws AcceloException
	{
		Affiliation.Response response;
		try
		{
			// String args = "_fields=_ALL,contact(_ALL)&filters=contact(" +
			// contact.getid() + ")";

			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("contact", contact.getid()));

			response = api.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter, AcceloFieldList.ALL,
					Affiliation.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	public static List<Affiliation> getByActivity(AcceloApi acceloApi, Activity activity) throws AcceloException
	{
		Affiliation.Response response;
		try
		{
			// String args = "_fields=_ALL,contact(_ALL)&filters=contact(" +
			// contact.getid() + ")";

			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("activity", activity.getId()));

			response = acceloApi.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter, AcceloFieldList.ALL,
					Affiliation.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return response.getList();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "Affiliation [id=" + id + ", company=" + company + ", postal_address=" + postal_address
				+ ", physical_address=" + physical_address + ", phone=" + phone + ", contact=" + contact + ", mobile="
				+ mobile + ", email=" + email + ", position=" + position + ", date_last_interacted="
				+ date_last_interacted + ", date_modified=" + date_modified + ", staff_bookmarked=" + staff_bookmarked
				+ ", standing=" + standing + "]";
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getCompany()
	{
		return company;
	}

	public void setCompany(String company)
	{
		this.company = company;
	}

	public int getPostal_address()
	{
		return postal_address;
	}

	public void setPostal_address(int postal_address)
	{
		this.postal_address = postal_address;
	}

	public int getPhysical_address()
	{
		return physical_address;
	}

	public void setPhysical_address(int physical_address)
	{
		this.physical_address = physical_address;
	}

	public int getContactId()
	{
		return contact;
	}

	public void setContact(int contact)
	{
		this.contact = contact;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public Date getDate_last_interacted()
	{
		return new Date(date_last_interacted);
	}

	public void setDate_last_interacted(Date date_last_interacted)
	{
		this.date_last_interacted = date_last_interacted.getTime();
	}

	public Date getDate_modified()
	{
		return new Date(date_modified);
	}

	public void setDate_modified(Date date_modified)
	{
		this.date_modified = date_modified.getTime();
	}

	public int getStaff_bookmarked()
	{
		return staff_bookmarked;
	}

	public void setStaff_bookmarked(int staff_bookmarked)
	{
		this.staff_bookmarked = staff_bookmarked;
	}

	public String getStanding()
	{
		return standing;
	}

	public void setStanding(String standing)
	{
		this.standing = standing;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPosition()
	{
		return position;
	}

}
