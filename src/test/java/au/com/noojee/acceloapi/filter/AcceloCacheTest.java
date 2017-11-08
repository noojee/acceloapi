package au.com.noojee.acceloapi.filter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import au.com.noojee.acceloapi.entities.AcceloEntity;

public class AcceloCacheTest
{
	Logger logger = LogManager.getLogger();

	@Test
	public void testEviction()
	{

		List<MockEntity> cachedList = Arrays.asList(new MockEntity(0), new MockEntity(1), new MockEntity(2), new MockEntity(3), new MockEntity(4));
		List<MockEntity> list = Arrays.asList(cachedList.get(1), cachedList.get(2));

		List<MockEntity> badEntities = cachedList.stream().filter(entity -> !list.contains(entity))
				.collect(Collectors.toList());

		// evict any of the badEntities
		badEntities.stream().forEach(entity -> logger.error("evicting" + entity));
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
