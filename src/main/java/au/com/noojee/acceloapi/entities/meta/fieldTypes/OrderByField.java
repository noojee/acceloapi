package au.com.noojee.acceloapi.entities.meta.fieldTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderByField
{
	enum Order
	{
		ASC, DESC
	};

	
	Order order() default Order.ASC;
}
