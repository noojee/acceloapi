package au.com.noojee.acceloapi.entities.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceloField
{
	enum Type { FILTER, SEARCH, BOTH, DATE};
	
	Type value() default Type.FILTER;

	String name() default "";
}
