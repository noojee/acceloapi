package au.com.noojee.acceloapi.entities;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.FilterField;

public abstract class AcceloEntity<E extends AcceloEntity<E>> implements Comparable<E>, Cloneable
{

	@BasicFilterField
	private int id;

	public int getId()
	{
		return id;
	}

	@Override
	public int compareTo(E rhs)
	{
		return this.id - rhs.getId();
	}

	

	/**
	 * Special - used by the AcceloCache. Don't go there.
	 * 
	 * @return
	 */
	public FilterField<E, Integer> getIdFilterField()
	{
		return new FilterField<>("id");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		@SuppressWarnings("rawtypes")
		AcceloEntity other = (AcceloEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}