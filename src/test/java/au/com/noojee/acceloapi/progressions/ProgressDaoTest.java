package au.com.noojee.acceloapi.progressions;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.dao.ProgressionDao;
import au.com.noojee.acceloapi.dao.TicketDao;
import au.com.noojee.acceloapi.entities.Progression;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.meta.Ticket_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class ProgressDaoTest
{

	@Test
	public void test()
	{
		AcceloSecret secret;
		try
		{
			secret = AcceloSecret.load();
			AcceloApi.getInstance().connect(secret);
			
			
			ProgressionDao daoProgression = new ProgressionDao();
			
			AcceloFilter<Ticket> filter = new AcceloFilter<>();
			filter.where(filter.eq(Ticket_.id, 18017));
			
			List<Ticket> tickets = new TicketDao().getByFilter(filter);
			
			Ticket ticket = tickets.get(0);
			
			List<Progression> progressions = daoProgression.getByEndPoint(EndPoint.tickets, ticket);
			
			for (Progression progression : progressions)
			{
				System.out.println(progression);
			}
			
			daoProgression.triggerProgression(progressions.get(0).getId(), tickets.get(0).getId());

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
