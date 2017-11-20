package au.com.noojee.acceloapi.dao;

import java.time.LocalDate;
import java.util.List;

import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contact;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.ContractPeriod;
import au.com.noojee.acceloapi.entities.meta.Contract_;
import au.com.noojee.acceloapi.entities.types.AgainstType;
import au.com.noojee.acceloapi.filter.AcceloFilter;
import au.com.noojee.acceloapi.util.Constants;

public class ContractDao extends AcceloDao<Contract>
{
	

	/**
	 * Find an active Contract Period for the given phone no. We also trying
	 * stripping the area code in case the phone no. is stored as just eight
	 * digits.
	 * @param phone
	 * 
	 * @return
	 * @throws AcceloException
	 */
	public Contract getActiveContractByPhone(String phone) throws AcceloException
	{
		Contract active = null;

		if (phone.length() != 10)
			throw new AcceloException("The phone number must be 10 digits long, found '" + phone + "'");

		List<Contact> contacts = new ContactDao().getByPhone(phone);
		if (contacts.size() == 0)
		{
			// Didn't find by the phone number.
			// List try trimming off the area code and see if that gives a
			// match.

			contacts = new ContactDao().getByPhone(phone.substring(2));
		}

		// If we get multiple matches we are just going to use the first one.
		// Its not the end of the world if we register against the company.
		if (contacts.size() >= 1)
		{
			Contact contact = contacts.get(0);

			Company company = new ContactDao().getDefaultCompany(contact);
			List<Contract> contracts = this.getByCompany(company);

			// find the first contract with a non-expired contract_period
			for (Contract contract : contracts)
			{
				List<ContractPeriod> periods = new ContractPeriodDao().getByContract(contract);
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
	 * Find an Active Contract for the given company.
	 * 
	 * This will return a random contract if the company has more than one activie contract.
	 * @param Company_
	 *            - we only us the Id of the company.
	 * 
	 * @return
	 * @throws AcceloException
	 */
	public Contract getActiveContract(Company company) throws AcceloException
	{
		Contract active = null;

		List<Contract> contracts = this.getByCompany(company);

		// find the first contract with a non-expired contract_period
		for (Contract contract : contracts)
		{
			List<ContractPeriod> periods = new ContractPeriodDao().getByContract(contract);
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
	public List<Contract> getActiveContracts() throws AcceloException
	{
		// Get all contracts where the expiry date is after today or null and the
		// start date is before today
		AcceloFilter<Contract> filter = new AcceloFilter<>();
		filter.where(filter.before(Contract_.date_started, LocalDate.now())
				.and(filter.after(Contract_.date_expires, LocalDate.now())
						.or(filter.empty(Contract_.date_expires)).or(filter.eq(Contract_.date_expires, Constants.DATEZERO))));

		return getByFilter(filter);
	}

	public List<Contract> getByCompany(Company company) throws AcceloException
	{
		List<Contract> contracts;
		AcceloFilter<Contract> filter = new AcceloFilter<>();
		filter.where(filter.against(AgainstType.company, company.getId()));
		contracts = getByFilter(filter);
		return contracts;
	}

	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.contracts;
	}

	
	@Override
	protected Class<Contract> getEntityClass()
	{
		return Contract.class;
	}
	
	public class ResponseList extends AcceloResponseList<Contract>
	{
	}

	public class Response extends AcceloResponse<Contract>
	{
	}


	@Override
	protected Class<ContractDao.ResponseList> getResponseListClass()
	{
		return ContractDao.ResponseList.class;
	}

	@Override
	protected Class<? extends AcceloResponse<Contract>> getResponseClass()
	{
		return ContractDao.Response.class;
	}

}
