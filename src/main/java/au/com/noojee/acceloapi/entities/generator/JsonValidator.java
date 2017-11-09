package au.com.noojee.acceloapi.entities.generator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.annotations.SerializedName;

public class JsonValidator
{
	private static Logger logger = LogManager.getLogger();
	

	public static <E> void validate(Class<E> entityClass, HashMap<String, String> fields)
	{
		logger.error("Validating class: " + entityClass.getSimpleName());
		// now we do the validation
		if (compareFields(fields, entityClass))
		{
			logger.error("No problems found for class: " + entityClass.getSimpleName());
		}
		logger.error("-----------------");
		
	}
	
	private static <E> boolean compareFields(HashMap<String, String> jsonFields, Class<E> entityClass)
	{
		boolean noProblems = true;
		
		List<Field> fields = new ArrayList<>();
		fields.addAll(Arrays.asList(entityClass.getDeclaredFields()));
		fields.addAll(Arrays.asList(entityClass.getSuperclass().getDeclaredFields()));

		// Check if each field in the hashmap has a corresponding field in the entity
		for (String jsonField : jsonFields.keySet())
		{
			boolean found = fields.stream().anyMatch(field -> jsonField.compareTo(getFieldName(field)) == 0);
			if (!found)
			{
				noProblems = false;
				logger.error("Json field: " + jsonField + " 		: missing from entity");
			}

		}

		// Now check that each field in the entity is in the json data.
		for (Field f : fields)
		{

			int modifiers = f.getModifiers();
			if (Modifier.isPrivate(modifiers) && !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers))
			{
				try
				{
					f.setAccessible(true);

					boolean found = jsonFields.keySet().stream()
							.anyMatch(jsonField -> jsonField.compareTo(getFieldName(f)) == 0);
					if (!found)
					{
						noProblems = false;
						logger.error("Entity field: " + f.getName() + " 		: missing from json");
					}

				}
				catch (IllegalArgumentException ex)
				{
					logger.error(ex, ex);
				}
			}
		}
		return noProblems;
	}

	private static String getFieldName(Field field)
	{
		SerializedName annotation = field.getAnnotation(SerializedName.class);
		
		String fieldName = field.getName();
		if (annotation != null)
			fieldName = annotation.value();
		
		return fieldName;
	}

}
