package au.com.noojee.acceloapi;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.noojee.acceloapi.cache.AcceloCache;
import au.com.noojee.acceloapi.dao.ActivityDao;
import au.com.noojee.acceloapi.dao.CompanyDao;
import au.com.noojee.acceloapi.dao.ContractDao;
import au.com.noojee.acceloapi.dao.TicketDao;
import au.com.noojee.acceloapi.entities.Activity;
import au.com.noojee.acceloapi.entities.Company;
import au.com.noojee.acceloapi.entities.Contract;
import au.com.noojee.acceloapi.entities.Ticket;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	private static Logger logger = LogManager.getLogger();

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 * @throws FileNotFoundException
	 * @throws AcceloException
	 */
	public AppTest(String testName) throws FileNotFoundException, AcceloException
	{
		super(testName);

		AcceloApi.getInstance().connect(AcceloSecret.load());
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(AppTest.class);

	}

	/**
	 * Rigourous Test :-)
	 * @throws AcceloException 
	 */
	public void testApp() throws AcceloException
	{

		AcceloCache cache = AcceloCache.getInstance();
		
		List<Contract> contracts = new ContractDao().getActiveContracts();

		for (Contract contract : contracts)
		{
			
			Company company = new CompanyDao().getById(contract.getCompanyId());
			logger.error("Misscounter =" + cache.getMissCounter());
			cache.resetMissCounter();
			// do it again to test the cache.
			new CompanyDao().getById(company.getId());
			assertTrue("Company should come from cache", cache.getMissCounter() == 0);
			
			
			assertTrue("Company should come from cache", cache.getMissCounter() == 0);

			LocalDate firstDateOfInterest = LocalDate.of(2017, 8, 1);
			List<Ticket> tickets = new TicketDao().getRecentByContract(contract, firstDateOfInterest);
			
			cache.resetMissCounter();
			tickets = new TicketDao().getRecentByContract(contract, firstDateOfInterest);
			assertTrue("Tickets should come from cache", cache.getMissCounter() == 0);
			
			for (Ticket ticket : tickets)
			{
				
				List<Activity> activities = new ActivityDao().getByTicket(ticket);
				
				cache.resetMissCounter();
				new ActivityDao().getByTicket(ticket);
				assertTrue("Activities should come from cache", cache.getMissCounter() == 0);
				
				
				cache.resetMissCounter();

				for (Activity activity : activities)
				{
					int ticketid = activity.getOwnerId();
					new TicketDao().getById(ticketid);
					
					// test the cache.
					cache.resetMissCounter();
					new TicketDao().getById(ticketid);
					assertTrue("Ticket should come from cache", cache.getMissCounter() == 0);
					
				}
			}
		}

	}

}
