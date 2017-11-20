package au.com.noojee.acceloapi.entities.generator;

import static org.reflections.ReflectionUtils.getAllFields;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.MetaBasicFilterFields;

public class FieldMetaDataGenerator
{

	private static final String AU_COM_NOOJEE_ACCELOAPI_ENTITIES = "au.com.noojee.acceloapi.entities";

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public static void main(String[] args)
	{

		Reflections reflections = new Reflections(AU_COM_NOOJEE_ACCELOAPI_ENTITIES);

		Set<Class<? extends AcceloEntity>> entityClasses = reflections.getSubTypesOf(AcceloEntity.class);

		for (Class<? extends AcceloEntity> entityClass : entityClasses)
		{
			Set<Field> fields = getAllFields(entityClass);

			writeMetaClass(entityClass, fields);

		}

	}

	private static void writeMetaClass(@SuppressWarnings("rawtypes") Class<? extends AcceloEntity> entityClass,
			Set<Field> fields)
	{
		File workingDir = new File(System.getProperty("user.dir"), "src/main/java");
		File packageDir = new File((AU_COM_NOOJEE_ACCELOAPI_ENTITIES + ".meta").replace('.', File.separatorChar));

		File classFile = new File(workingDir, new File(packageDir, entityClass.getSimpleName() + "_.java").getPath());

		fields.addAll(getMetaSearchFields(entityClass));

		try (PrintWriter pf = new PrintWriter(classFile))
		{
			String className = entityClass.getSimpleName();

			writeIntro(pf);

			writeImports(fields, pf, className);

			/**
			 * Finally generate the class
			 */
			pf.println();
			pf.println();
			pf.println("public class " + className + "_ ");
			pf.println("{");
			pf.println();

			writeFields(fields, pf, className);

			pf.println();
			pf.println("}");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private static Set<Field> getMetaSearchFields(@SuppressWarnings("rawtypes") Class<? extends AcceloEntity> entityClass)
	{
		Set<Field> fields = new HashSet<>();

		Reflections reflections = new Reflections(entityClass);

		// Get the metaSearchField class.
		// There should be 0 or one of these per entity.
		Set<Class<? extends MetaBasicFilterFields>> metaSearchClasses = reflections
				.getSubTypesOf(MetaBasicFilterFields.class);

		// the above query returns every class that matches not just the ones related to entityClass so we have to
		// filter the list
		metaSearchClasses = metaSearchClasses.stream().filter(c -> c.getName().startsWith(entityClass.getName()))
				.collect(Collectors.toSet());

		if (metaSearchClasses.size() > 1)
		{
			throw new IllegalStateException("The class " + entityClass.getName()
					+ " has more than one MetaSearchField. Only 0 or 1 is allowed.");
		}
		else if (metaSearchClasses.size() == 1)
		{
			for (Class<? extends MetaBasicFilterFields> claz : metaSearchClasses)
			{
				fields = getAllFields(claz);
			}
			// MetaSearchFields[] searchClass = new MetaSearchFields[] {new MetaSearchFields() {} };
			// Class< ? extends MetaSearchFields>[] searchClass = (Class<? extends MetaSearchFields>[])
			// metaSearchClasses.toArray();

		}
		return fields;
	}

	private static void writeFields(Set<Field> fields, PrintWriter pf, String className)
	{
		
		
		/**
		 * Now out put a line per annotated filed.
		 */
		for (Field field : fields.stream().sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList()))
		{
			Class<?> type = field.getType();
			Class<?> objectType = toObjectType(type);
			String javaFieldName = field.getName();
			

			if (field.isAnnotationPresent(BasicFilterField.class))
			{
				BasicFilterField annotation = field.getAnnotation(BasicFilterField.class);
				
				String acceloFieldName = annotation.name();
				if (annotation.name().length() == 0)
					acceloFieldName = javaFieldName;
				
				pf.println("\tpublic static FilterField<" + className + ", " + objectType.getSimpleName() + "> "
						+ javaFieldName + " = new FilterField<>(\"" + acceloFieldName + "\"); ");
			}
			else if (field.isAnnotationPresent(DateFilterField.class))
			{
				DateFilterField annotation = field.getAnnotation(DateFilterField.class);
				
				String acceloFieldName = annotation.name();
				if (annotation.name().length() == 0)
					acceloFieldName = javaFieldName;
	
				pf.println("\tpublic static FilterField<" + className + ", LocalDate> "
						+ javaFieldName + " = new FilterField<>(\"" + acceloFieldName + "\"); ");
			}

		}
	}

	private static void writeImports(Set<Field> fields, PrintWriter pf, String className)
	{
		Set<String> imports = determinImports(className, fields);

		/**
		 * Now output the imports
		 */
		for (String imported : imports)
		{
			pf.println("import " + imported + ";");
		}
	}

	private static void writeIntro(PrintWriter pf)
	{
		pf.println("package au.com.noojee.acceloapi.entities.meta;");
		pf.println();
		pf.println("/** ");
		pf.println(" *");
		pf.println(" *          DO NOT MODIFY ");
		pf.println(" *");
		pf.println(" * This code is generated by " + FieldMetaDataGenerator.class.getName());
		pf.println(" *");
		pf.println(
				" * The generator use @AcceloField annotations to determine what fields to include in the Meta data.");
		pf.println(" *");
		pf.println(" *          DO NOT MODIFY ");
		pf.println(" *");
		pf.println(" */");
	}

	private static Set<String> determinImports(String className, Set<Field> fields)
	{
		Set<String> imports = new HashSet<String>();

		imports.add(au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField.class.getCanonicalName());
		imports.add("au.com.noojee.acceloapi.entities." + className);

		/**
		 * Find any custom imports we need to output.
		 */
		for (Field field : fields)
		{
			Class<?> type = field.getType();
			if (field.isAnnotationPresent(BasicFilterField.class) || field.isAnnotationPresent(DateFilterField.class))
			{
				if (!type.getName().startsWith("java.lang") && !type.isPrimitive())
				{
					// nested packages have a $ in the name.
					imports.add(type.getName().replace('$', '.'));
				}
				
			}
		}
		return imports;
	}

	private static Class<?> toObjectType(Class<?> type)
	{
		Class<?> convertedType = type;

		if (type == long.class)
		{
			convertedType = Long.class;
		}
		else if (type == int.class)
		{
			convertedType = Integer.class;
		}
		if (type == float.class)
		{
			convertedType = Float.class;
		}
		if (type == double.class)
		{
			convertedType = Double.class;
		}
		return convertedType;
	}

}

/**
 * package au.com.noojee.acceloapi.entities.meta; import au.com.noojee.acceloapi.entities.Company; public class Company_
 * { public static FilterField<Company, Integer> id = new FilterField<>("id"); }
 */
