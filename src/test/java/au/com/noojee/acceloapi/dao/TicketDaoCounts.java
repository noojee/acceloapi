package au.com.noojee.acceloapi.dao;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.Ticket.Standing;
import au.com.noojee.acceloapi.entities.meta.Ticket_;
import au.com.noojee.acceloapi.filter.AcceloFilter;

public class TicketDaoCounts
{

	@Test
	public void test() throws FileNotFoundException
	{
		AcceloSecret secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);
		TicketDao daoTicket = new TicketDao();

		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		
		filter.where(filter.eq(Ticket_.contract, 0)).and(filter.after(Ticket_.date_submitted, LocalDateTime.of(2017, 03, 01, 0, 0, 0)).and(filter.eq(Ticket_.standing, Standing.closed)));
		
		filter.limit(10000);
		
		List<Ticket> tickets = daoTicket.getByFilter(filter);
		
		System.out.println("Total Tickets with No Contract" + tickets.size());

	}

}
