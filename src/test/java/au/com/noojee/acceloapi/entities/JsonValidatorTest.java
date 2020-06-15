package au.com.noojee.acceloapi.entities;

import java.io.FileNotFoundException;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;

public class JsonValidatorTest
{

	@Test
	public void test() throws FileNotFoundException
	{
		AcceloApi.getInstance().connect(AcceloSecret.load());
		
//		new ActivityDao().validateEntityClass();
//		new AffiliationDao().validateEntityClass();
//		//new CompanyDao().validateEntityClass();
//		new ContractDao().validateEntityClass();
//		new ContractDao().validateEntityClass();
//		// new ContractPeriodDao().validateEntityClass();
//		new InvoiceDao().validateEntityClass();
//		new StaffDao().validateEntityClass();
//		new TicketDao().validateEntityClass();
//		// new TimeAllocationDao().validateEntityClass();
	}
}
