package au.com.noojee.acceloapi.entities;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.Meta;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.filter.expressions.After;
import au.com.noojee.acceloapi.filter.expressions.Before;
import au.com.noojee.acceloapi.filter.expressions.Compound;
import au.com.noojee.acceloapi.filter.expressions.Eq;
import au.com.noojee.acceloapi.filter.expressions.Expression;

public class Contract implements Serializable
{

	private static final long serialVersionUID = 1L;
	int id;
	String company;			// The owning company id.
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

	/**
	 * Find an active Contract Period for the given phone no. We also trying
	 * stripping the area code in case the phone no. is stored as just eight
	 * digits.
	 * 
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
			// List try trimming off the area code and see if that gives a
			// match.

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
					LocalDate expires = period.getDateExpires();
					LocalDate now = LocalDate.now();
					if (expires.isBefore(now)) // expired
						continue;

					LocalDate commenced = period.getDateCommenced();
					if (commenced.isAfter(now))
						continue; // hasn't started yet.

					active = contract;
					break;
				}
			}

		}

		return active;

	}

	/**
	 * Find an Contract for the given company.
	 * 
	 * @param Company
	 *            - we only us the Id of the company.
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
				LocalDate expires = period.getDateExpires();
				LocalDate now = LocalDate.now();
				if (expires.isBefore(now)) // expired
					continue;

				LocalDate commenced = period.getDateCommenced();
				if (commenced.isAfter(now))
					continue; // hasn't started yet.

				active = contract;
				break;
			}
		}

		return active;

	}

	/**
	 * Get a complete list of the active contracts.
	 * 
	 * @param acceloApi
	 * @throws AcceloException
	 */
	public static List<Contract> getActiveContracts(AcceloApi acceloApi) throws AcceloException
	{
		List<Contract> contracts = new ArrayList<>();

		try
		{
			// Get all contracts where the expiry date is after today and the
			// start date is before today
			AcceloFilter filter = new AcceloFilter();
			filter.add(new Before("date_started", LocalDate.now()));
			filter.add(new After("date_expires", LocalDate.now()));
			

			contracts = acceloApi.getAll(EndPoint.contracts, filter, AcceloFieldList.ALL, Contract.ResponseList.class);

			// Do it again looking for contracts with a null expiry date.

			filter = new AcceloFilter();
			//filter.add(new Empty("date_expires")); // Empty on date_expires doesn't currently work and we seem to get back ad ate of 1970.
			filter.add(new Before("date_started", LocalDate.now()));
			filter.add(new Eq("date_expires", Expression.DATE1970)); // 1/1/1970

			contracts.addAll(acceloApi.getAll(EndPoint.contracts, filter, AcceloFieldList.ALL, Contract.ResponseList.class));
			

		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return contracts;

	}

	public static List<Contract> getByCompany(AcceloApi acceloApi, Company company) throws AcceloException
	{
		Contract.ResponseList request;
		try
		{
			AcceloFilter filters = new AcceloFilter();

			filters.add(new Compound("against")).add(new Eq("company", company.getId()));
			request = acceloApi.get(EndPoint.contracts, filters, AcceloFieldList.ALL, Contract.ResponseList.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return request.getList();

	}

	public List<ContractPeriod> getContractPeriods(AcceloApi acceloApi) throws AcceloException
	{

		List<ContractPeriod> periods;
		try
		{
			periods = acceloApi.getAll(EndPoint.contracts.getURL(this.getId(), "/periods"), null, AcceloFieldList.ALL,
					ResponseContactPeriods.class);
		}
		catch (IOException e)
		{
			throw new AcceloException(e);
		}

		return periods;

	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public class ResponseList extends AcceloResponseList<Contract>
	{
	}

	public class aResponse // extends AcceloResponseList<Contract_period>
	{
		Meta meta;
		ResponseContactPeriods response;
	}

	public class ResponseContactPeriods extends AcceloResponseList<ContractPeriod>
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
	 * 
	 * @return
	 */
	public double getValue()
	{
		return value;
	}

	public LocalDate getDateCreated()
	{
		return AcceloApi.toLocalDate(date_created);
	}

	public LocalDate getDateStarted()
	{
		return AcceloApi.toLocalDate(date_started);
	}

	public LocalDate getDatePeriodExpires()
	{
		return AcceloApi.toLocalDate(date_period_expires);
	}

	public LocalDate getDateExpires()
	{
		return AcceloApi.toLocalDate(date_expires);
	}

	public LocalDate getDateLastInteracted()
	{
		return AcceloApi.toLocalDate(date_last_interacted);
	}

	public int getRenewDays()
	{
		return renew_days;
	}

	public String getStanding()
	{
		return standing;
	}

	public String getAutoRenew()
	{
		return auto_renew;
	}

	public String getDeployment()
	{
		return deployment;
	}

	public String getAgainst()
	{
		return against;
	}

	public String getAgainstType()
	{
		return against_type;
	}

	public String getManager()
	{
		return manager;
	}

	public String getJob()
	{
		return job;
	}

	public String getStatus()
	{
		return status;
	}

	public int getBillable()
	{
		return billable;
	}

	public String getSendInvoice()
	{
		return send_invoice;
	}

	public String getStaffBookmarked()
	{
		return staff_bookmarked;
	}

	public int getType()
	{
		return type;
	}

	public int getAgainstId()
	{
		return against_id;
	}

	public String getNotes()
	{
		return notes;
	}

	public int getPeriodTemplateId()
	{
		return period_template_id;
	}

	public int getCompanyId()
	{
		return Integer.valueOf(company);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((against == null) ? 0 : against.hashCode());
		result = prime * result + against_id;
		result = prime * result + ((against_type == null) ? 0 : against_type.hashCode());
		result = prime * result + ((auto_renew == null) ? 0 : auto_renew.hashCode());
		result = prime * result + billable;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + (int) (date_created ^ (date_created >>> 32));
		result = prime * result + (int) (date_expires ^ (date_expires >>> 32));
		result = prime * result + (int) (date_last_interacted ^ (date_last_interacted >>> 32));
		result = prime * result + (int) (date_period_expires ^ (date_period_expires >>> 32));
		result = prime * result + (int) (date_started ^ (date_started >>> 32));
		result = prime * result + ((deployment == null) ? 0 : deployment.hashCode());
		result = prime * result + id;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + period_template_id;
		result = prime * result + renew_days;
		result = prime * result + ((send_invoice == null) ? 0 : send_invoice.hashCode());
		result = prime * result + ((staff_bookmarked == null) ? 0 : staff_bookmarked.hashCode());
		result = prime * result + ((standing == null) ? 0 : standing.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + type;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (against == null)
		{
			if (other.against != null)
				return false;
		}
		else if (!against.equals(other.against))
			return false;
		if (against_id != other.against_id)
			return false;
		if (against_type == null)
		{
			if (other.against_type != null)
				return false;
		}
		else if (!against_type.equals(other.against_type))
			return false;
		if (auto_renew == null)
		{
			if (other.auto_renew != null)
				return false;
		}
		else if (!auto_renew.equals(other.auto_renew))
			return false;
		if (billable != other.billable)
			return false;
		if (company == null)
		{
			if (other.company != null)
				return false;
		}
		else if (!company.equals(other.company))
			return false;
		if (date_created != other.date_created)
			return false;
		if (date_expires != other.date_expires)
			return false;
		if (date_last_interacted != other.date_last_interacted)
			return false;
		if (date_period_expires != other.date_period_expires)
			return false;
		if (date_started != other.date_started)
			return false;
		if (deployment == null)
		{
			if (other.deployment != null)
				return false;
		}
		else if (!deployment.equals(other.deployment))
			return false;
		if (id != other.id)
			return false;
		if (job == null)
		{
			if (other.job != null)
				return false;
		}
		else if (!job.equals(other.job))
			return false;
		if (manager == null)
		{
			if (other.manager != null)
				return false;
		}
		else if (!manager.equals(other.manager))
			return false;
		if (notes == null)
		{
			if (other.notes != null)
				return false;
		}
		else if (!notes.equals(other.notes))
			return false;
		if (period_template_id != other.period_template_id)
			return false;
		if (renew_days != other.renew_days)
			return false;
		if (send_invoice == null)
		{
			if (other.send_invoice != null)
				return false;
		}
		else if (!send_invoice.equals(other.send_invoice))
			return false;
		if (staff_bookmarked == null)
		{
			if (other.staff_bookmarked != null)
				return false;
		}
		else if (!staff_bookmarked.equals(other.staff_bookmarked))
			return false;
		if (standing == null)
		{
			if (other.standing != null)
				return false;
		}
		else if (!standing.equals(other.standing))
			return false;
		if (status == null)
		{
			if (other.status != null)
				return false;
		}
		else if (!status.equals(other.status))
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

}
