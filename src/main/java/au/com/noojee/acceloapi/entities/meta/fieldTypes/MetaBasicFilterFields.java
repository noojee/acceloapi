package au.com.noojee.acceloapi.entities.meta.fieldTypes;

/**
 * This interface is used to define the set of fields that Accelo allows a user to peform basic filters on,
 * but which are not actually returned as part of the entity.
 * 
 * Add a sub class 'Meta' which inherits from 'MetaBasicFilterFields' and then defined
 * each of the 'meta' fields which can be searched on. 
 * 
 * <pre>
 * <code>
	private class Meta implements MetaBasicFilterFields
	{
		{@literal @}BasicFilterField
		private transient String affiliation; // matches on the default_affiliation.
		
		{@literal @}BasicFilterField
		private transient String contact_number;  // filters over phone, fax and mobile
	}
	</code>
 * </pre>
 * 
 * @author bsutton
 *
 */
public interface MetaBasicFilterFields
{

	
}
