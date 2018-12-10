package au.com.noojee.acceloapi.entities.meta.fieldTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation designates that the field is required when 'inserting' an entity into 
 * Acceol.
 * 
 * These fields are not part of the normal entity attributes and are not available when retrieving the entity.
 * 
 * As an example the 'Contact' entity requires the company_id to be provide on inserts even though the company_id
 * isn't stored on the contact. Instead Accelo uses this field to create the default affiliation.
 * 
 * @author bsutton
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InsertOnlyField 
{
	String name() default "";
}
