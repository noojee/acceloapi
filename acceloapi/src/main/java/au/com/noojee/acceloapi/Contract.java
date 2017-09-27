package au.com.noojee.acceloapi;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Contract implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	int id;
	String company;
	String title;
	long date_created;
	long date_started;
	long date_period_expires;
	long date_expires;
	long date_last_interacted;
	int renew_days;
	String standing;
	String auto_renew;
	String deployment;
	String against;
	

	String against_type;
	String manager;
	String job;
	double value;
	String status;
	int billable;
	String send_invoice;
	String staff_bookmarked;
	int type;
	int against_id;
	String notes;
	int period_template_id;

	// fields obtain by additional api calls
	private Company ownerCompany = null;

	// These values are set based on the contents of the title.
	// Premium - 24 hour support - 24 x 7
	// Standard - 8am to 8pm - Victorian Business Days
	// Basic - 9am to 5pm - Victorian Business Days

	boolean allHours = true;
	int startTime = 9;
	int endTime = 5;
	boolean nonBusinessDaysAllowed = false;

	public void setSupportHours()
	{
		if (title.toLowerCase().contains("premium"))
		{
			allHours = true;
			nonBusinessDaysAllowed = true;
		}
		else if (title.toLowerCase().contains("standard"))
		{
			allHours = false;
			startTime = 8;
			endTime = 20;
			nonBusinessDaysAllowed = false;
		}
		else if (title.toLowerCase().contains("basic"))
		{
			allHours = false;
			startTime = 9;
			endTime = 17;
			nonBusinessDaysAllowed = false;
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		setSupportHours();
	}

	public boolean isInSupportHours(boolean isBusinessDay)
	{
		boolean supportAvailable = false;

		Calendar now = Calendar.getInstance();

		if (allHours)
		{
			supportAvailable = true;
		}
		else if (now.get(Calendar.HOUR) >= startTime && now.get(Calendar.HOUR) < endTime)
		{
			// In reality this method should only ever be called out of hours.
			supportAvailable = isBusinessDay || nonBusinessDaysAllowed;
		}
		return supportAvailable;
	}

	/** 
	 * Find an active Contract Period for the given phone no.
	 * We also trying stripping the area code in case the phone no. is stored as just eight digits.
	 * @param phone
	 * @return
	 * @throws AcceloException
	 */
	public static Contract getActiveContractByPhone(AcceloApi acceloApi, String phone) throws AcceloException
	{
		Contract active = null;

		if (phone.length() != 10)
			throw new AcceloException("The phone number must be 10 digits long, found '" + phone + "'");

		List<Contact> contacts = Contact.getByPhone(acceloApi, phone);
		if (contacts.size() == 0)
		{
			// Didn't find by the phone number. 
			// List try trimming off the area code and see if that gives a match.

			contacts = Contact.getByPhone(acceloApi, phone.substring(2));
		}

		// If we get multiple matches we are just going to use the first one.
		// Its not the end of the world if we register against the company.
		if (contacts.size() >= 1)
		{
			Contact contact = contacts.get(0);

			Company company = contact.getCompany();
			List<Contract> contracts = Contract.getByCompany(acceloApi, company);

			// find the first contract with a non-expired contract_period
			for (Contract contract : contracts)
			{
				List<ContractPeriod> periods = contract.getContractPeriods(acceloApi);
				for (ContractPeriod period : periods)
				{
					Date expires = period.getDateExpires();
					Date now = new Date();
					if (expires.before(now)) // expired
						continue;

					Date commenced = period.getDateCommenced();
					if (commenced.after(now))
						continue; // hasn't started yet.

					active = contract;
					active.setCompany(company);
					break;
				}
			}

		}

		return active;

	}

	/** 
	 * Find an Contract for the given company.
	 * @param Company - we only us the Id of the company.
	 * @return
	 * @throws AcceloException
	 */
	public static Contract getActiveContract(AcceloApi acceloApi, Company company) throws AcceloException
	{
		Contract active = null;

		List<Contract> contracts = Contract.getByCompany(acceloApi, company);

		// find the first contract with a non-expired contract_period
		for (Contract contract : contracts)
		{
			List<ContractPeriod> periods = contract.getContractPeriods(acceloApi);
			for (ContractPeriod period : periods)
			{
				Date expires = period.getDateExpires();
				Date now = new Date();
				if (expires.before(now)) // expired
					continue;

				Date commenced = period.getDateCommenced();
				if (commenced.after(now))
					continue; // hasn't started yet.

				active = contract;
				active.setCompany(company);
				break;
			}
		}

		return active;

	}

	private void setCompany(Company company)
	{
		this.ownerCompany = company;

	}

	public Company getCompany()
	{
		return this.ownerCompany;
	}

	public static List<Contract> getByCompany(AcceloApi acceloApi, Company company) throws AcceloException
	{
		Contract.Response request;
		try
		{
			AcceloFilter filters = new AcceloFilter();
			
			filters.add(new AcceloFilter.CompoundMatch("against")).add(new AcceloFilter.SimpleMatch("company", company.getId()));
			request = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.contracts.getURL(), filters, AcceloFieldList.ALL, Contract.Response.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return request.getList();

	}

	public List<ContractPeriod> getContractPeriods(AcceloApi acceloApi) throws AcceloException
	{

		aResponse respose;
		try
		{
			respose = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.contracts.getURL(this.getId(), "/periods"), null, AcceloFieldList.ALL,aResponse.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return respose.response.getList();

	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
	    return title;
	}

	public class Response extends AcceloResponseList<Contract>
	{
	}

	public class aResponse // extends AcceloResponseList<Contract_period>
	{
		Meta meta;
		ResponseContactPeriods response;
	}

	public class ResponseContactPeriods
	{

		List<ContractPeriod> periods;

		public List<ContractPeriod> getList()
		{
			return periods;
		}

	}

	@Override
	public String toString()
	{
		return "Contract [id=" + id + ", company=" + company + ", title=" + title + ", date_created=" + date_created
				+ ", date_started=" + date_started + ", date_period_expires=" + date_period_expires + ", date_expires="
				+ date_expires + ", date_last_interacted=" + date_last_interacted + ", renew_days=" + renew_days
				+ ", standing=" + standing + ", auto_renew=" + auto_renew + ", deployment=" + deployment + ", against="
				+ against + ", against_type=" + against_type + ", manager=" + manager + ", job=" + job + ", value="
				+ value + ", status=" + status + ", billable=" + billable + ", send_invoice=" + send_invoice
				+ ", staff_bookmarked=" + staff_bookmarked + ", type=" + type + ", against_id=" + against_id
				+ ", notes=" + notes + ", period_template_id=" + period_template_id + "]";
	}

	/**
	 * The dollar amount this contract includes in value
	 * @return
	 */
	public double getValue()
	{
		return value;
	}

}
