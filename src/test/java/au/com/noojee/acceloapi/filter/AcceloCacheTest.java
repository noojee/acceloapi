package au.com.noojee.acceloapi.filter;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.AcceloSecret;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.cache.AcceloCache;
import au.com.noojee.acceloapi.cache.CacheKey;
import au.com.noojee.acceloapi.dao.TicketDao;
import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.Ticket;
import au.com.noojee.acceloapi.entities.meta.Ticket_;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.OrderByField.Order;

public class AcceloCacheTest
{
	Logger logger = LogManager.getLogger();

	@Test
	public void testEviction()
	{

		List<MockEntity> cachedList = Arrays.asList(new MockEntity(0), new MockEntity(1), new MockEntity(2),
				new MockEntity(3), new MockEntity(4));
		List<MockEntity> list = Arrays.asList(cachedList.get(1), cachedList.get(2));

		List<MockEntity> badEntities = cachedList.stream().filter(entity -> !list.contains(entity))
				.collect(Collectors.toList());

		// evict any of the badEntities
		badEntities.stream().forEach(entity -> logger.error("evicting" + entity));
	}

	@Test
	public void testHashcode()
	{
		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.contract, 0).and(filter.eq(Ticket_.standing, Ticket.Standing.closed))
				.and(filter.after(Ticket_.date_started, LocalDateTime.of(2017, 03, 01, 0, 0, 0))))
				.orderBy(Ticket_.id, Order.DESC);
		filter.limit(1);
		filter.offset(2);

		CacheKey<Ticket> key = new CacheKey<Ticket>(EndPoint.tickets, filter, AcceloFieldList.ALL,
				TicketDao.ResponseList.class, Ticket.class);

		AcceloFilter<Ticket> filter2 = new AcceloFilter<>();
		filter2.where(filter2.eq(Ticket_.contract, 0).and(filter2.eq(Ticket_.standing, Ticket.Standing.closed))
				.and(filter2.after(Ticket_.date_started, LocalDateTime.of(2017, 03, 01, 0, 0, 0))))
				.orderBy(Ticket_.id, Order.DESC);

		filter2.limit(1);
		filter2.offset(2);

		CacheKey<Ticket> key2 = new CacheKey<Ticket>(EndPoint.tickets, filter2, AcceloFieldList.ALL,
				TicketDao.ResponseList.class, Ticket.class);

		System.out.println("key: " + key.hashCode());

		Assert.assertEquals(key.hashCode(), key2.hashCode());
		Assert.assertTrue(key.equals(key2));

	}

	@Test
	public void testCacheMatch() throws FileNotFoundException
	{
		AcceloSecret secret;
		secret = AcceloSecret.load();
		AcceloApi.getInstance().connect(secret);

		TicketDao daoTicket = new TicketDao();

		AcceloFilter<Ticket> filter = new AcceloFilter<>();
		filter.where(filter.eq(Ticket_.contract, 0).and(filter.eq(Ticket_.standing, Ticket.Standing.closed))
				.and(filter.after(Ticket_.date_started,LocalDateTime.of(2017, 03, 01, 0, 0, 0))))
				.orderBy(Ticket_.id, Order.DESC);
		filter.limit(1);
		filter.offset(0);

		AcceloFilter<Ticket> savedFilter = filter.copy(); 

		daoTicket.getByFilter(filter);
		
		// Check that repeating the query causes a cache hit.
		AcceloCache.getInstance().resetMissCounter();
		daoTicket.getByFilter(filter);
		Assert.assertTrue(AcceloCache.getInstance().getMissCounter() == 0);

		// The filter forms part of the cache key and the key must be invariant
		// so by re-using the filter we check the key is invariant.
		filter.where(filter.eq(Ticket_.contract, 0).and(filter.eq(Ticket_.standing, Ticket.Standing.closed))
				.and(filter.after(Ticket_.date_started, LocalDateTime.of(2017, 03, 01, 0, 0, 0))))
				.orderBy(Ticket_.id, Order.DESC);
		filter.limit(1);
		filter.offset(1);

		daoTicket.getByFilter(filter);
		
		// The original version of the filter should still be cached
		AcceloCache.getInstance().resetMissCounter();
		daoTicket.getByFilter(savedFilter);
		Assert.assertTrue(AcceloCache.getInstance().getMissCounter() == 0);
	}

	class MockEntity extends AcceloEntity<MockEntity>
	{
		int id;

		private MockEntity(int id)
		{
			this.id = id;
		}

		private MockEntity(MockEntity mockEntity)
		{
			this.id = mockEntity.id;
		}

		@Override
		public int compareTo(MockEntity arg0)
		{
			return this.id - arg0.id;
		}

		@Override
		public int getId()
		{
			return id;
		}

		@Override
		public String toString()
		{
			return "MockEntity [id=" + id + "]";
		}

	}

}
