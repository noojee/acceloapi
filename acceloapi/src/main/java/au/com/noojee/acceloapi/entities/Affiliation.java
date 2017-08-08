package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloFilter;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.Contact;
import au.com.noojee.acceloapi.AcceloApi.EndPoints;
import au.com.noojee.acceloapi.AcceloApi.HTTPMethod;
import au.com.noojee.acceloapi.AcceloFilter.SimpleMatch;

public class Affiliation
{
	private int id;
	
	private String company;
	@SuppressWarnings("unused")
	private String postal_address;
	private String phone;
	private Contact contact;
	private String mobile;
	private String email;
	private String position;
	private String status_id;
	private String communication;
	private String invoice_method; // email, fax or postal.
	
	class ResponseList extends AcceloResponseList<Affiliation>
	{
		
	}
	
	

	public static List<Affiliation> getAffilications(AcceloApi api, int companyId, int contactId) throws AcceloException
	{
		Affiliation.ResponseList response;
		try
		{
			// String args = "_filters=company_id(" + companyId + "),contact_id(" + contactId + ")&_fields=_ALL";
			
			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("company_id", companyId)).add(new SimpleMatch("contact_id", contactId));
			
			
			
			response = api.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter,  AcceloFieldList.ALL, Affiliation.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}
		
		return response.getList();
	}

	
	public static List<Affiliation> getAffilicationsByPhone(AcceloApi api, String phone) throws AcceloException
	{
		Affiliation.ResponseList response;
		try
		{
			//String args = "_fields=_ALL,contact(_ALL)&filters=phone(" + phone + ")";
			// String args = "_filters=phone(" + phone + ")";
			
			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("phone", phone));
			
			response = api.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter, AcceloFieldList.ALL,  Affiliation.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}
		
		return response.getList();
	}


	
	public static List<Affiliation> getAffilicationsForContact(AcceloApi api, Contact contact) throws AcceloException
	{
		Affiliation.ResponseList response;
		try
		{
			// String args = "_fields=_ALL,contact(_ALL)&filters=contact(" + contact.getid() + ")";
			
			AcceloFilter filter = new AcceloFilter();
			filter.add(new SimpleMatch("contact", contact.getid()));
			
			response = api.pull(HTTPMethod.GET, EndPoints.affiliations.getURL(), filter, AcceloFieldList.ALL,  Affiliation.ResponseList.class);
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
		return "Affiliation [id=" + id + ", company=" + company + ", contact=" + contact + ", phone="
				+ phone + ", mobile=" + mobile + ", email=" + email + ", position=" + position + ", status_id="
				+ status_id + ", communication=" + communication + ", invoice_method=" + invoice_method + "]";
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


	public void setStatus_id(String status_id)
	{
		this.status_id = status_id;
	}


	public void setCommunication(String communication)
	{
		this.communication = communication;
	}


	public void setInvoice_method(String invoice_method)
	{
		this.invoice_method = invoice_method;
	}


}
