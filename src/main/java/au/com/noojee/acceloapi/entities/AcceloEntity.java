package au.com.noojee.acceloapi.entities;

import au.com.noojee.acceloapi.AcceloFieldList;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.OrderByField;

public abstract class AcceloEntity<E extends AcceloEntity<E>> implements Comparable<E>, Cloneable
{

	@BasicFilterField
	@OrderByField
	private int id;
	
	/**	
	 * This is a hack as accelo requires that we provide a list of 'return' fields 
	 * embedded in the entity we send when inserting or updating an entity.
	 * If you are performed a 'get' operation this field will be ignored.
	 */
	@SuppressWarnings("unused")
	private String[] _fields = new String[] {AcceloFieldList._ALL};

	public void setFieldList(AcceloFieldList acceloFieldList)
	{
		_fields = acceloFieldList.fields();
	}
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