package au.com.noojee.acceloapi.entities;

import java.io.FileNotFoundException;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.dao.ActivityDao;
import au.com.noojee.acceloapi.dao.AffiliationDao;
import au.com.noojee.acceloapi.dao.CompanyDao;
import au.com.noojee.acceloapi.dao.ContractDao;
import au.com.noojee.acceloapi.dao.ContractPeriodDao;
import au.com.noojee.acceloapi.dao.InvoiceDao;
import au.com.noojee.acceloapi.dao.StaffDao;
import au.com.noojee.acceloapi.dao.TicketDao;
import au.com.noojee.acceloapi.dao.TimeAllocationDao;

public class JsonValidatorTest
{

	@Test
	public void test() throws FileNotFoundException
	{
		AcceloApi.getInstance().connect(AcceloSecret.load());
		
		new ActivityDao().validateEntityClass();
		new AffiliationDao().validateEntityClass();
		new CompanyDao().validateEntityClass();
		new ContractDao().validateEntityClass();
		new ContractDao().validateEntityClass();
		// new ContractPeriodDao().validateEntityClass();
		new InvoiceDao().validateEntityClass();
		new StaffDao().validateEntityClass();
		new TicketDao().validateEntityClass();
		// new TimeAllocationDao().validateEntityClass();
	}
}
