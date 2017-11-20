package au.com.noojee.acceloapi.entities.meta.fieldTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation designates that the field can be used in basic filter query.
 * 
 * This annotation is used to generate the meta data fields for each entity.
 * 
 * If you add/remove an annotation you need to run FieldMetaDataGenerator to update the meta data classes
 * for each entity.
 * 
 * @author bsutton
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BasicFilterField 
{
	String name() default "";
}
