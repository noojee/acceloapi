package au.com.noojee.acceloapi.dao.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


/**
 * Class which allows for the customisation of Gson serialisation
 * 
 *  This class exists because Accelo uses inconsistent naming conventions
 *  for inserting and updating entities.
 *   
 * @author bsutton
 *
 * @param <C>
 */
public abstract class CustomisableTypeAdaptor<C> implements TypeAdapterFactory
{

	private final Class<C> customizedClass;

	public CustomisableTypeAdaptor(Class<C> customizedClass)
	{
		this.customizedClass = customizedClass;
	}

	@Override
	@SuppressWarnings("unchecked") // we use a runtime check to guarantee that 'C' and 'T' are equal
	public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
	{
		return type.getRawType() == customizedClass
				? (TypeAdapter<T>) customGsonAdapator(gson, (TypeToken<C>) type)
				: null;
	}

	private TypeAdapter<C> customGsonAdapator(Gson gson, TypeToken<C> type)
	{
		final TypeAdapter<C> delegate = gson.getDelegateAdapter(this, type);
		final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
		return new TypeAdapter<C>()
		{
			@Override
			public void write(JsonWriter out, C value) throws IOException
			{
				JsonElement tree = delegate.toJsonTree(value);
				beforeWrite(value, tree);
				elementAdapter.write(out, tree);
			}

			@Override
			public C read(JsonReader in) throws IOException
			{
				JsonElement tree = elementAdapter.read(in);
				afterRead(tree);
				return delegate.fromJsonTree(tree);
			}
		};
	}

	/**
	 * Override this to muck with {@code toSerialize} before it is written to the outgoing JSON stream.
	 */
	protected void beforeWrite(C source, JsonElement toSerialize)
	{
	}

	/**
	 * Override this to muck with {@code deserialized} before it parsed into the application type.
	 */
	protected void afterRead(JsonElement deserialized)
	{
	}
	

	/**
	 * 
	 * The following is example code on how to overload a Dao class with custom adaptors.
	 *
	@Override
	public String toJson(AcceloEntity<Ticket> entity, DaoOperation operation)
	{
		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(new TicketUpdateTypeAdapterFactory())
				.create();
		return gson.toJson(entity);
	}

	private class TicketUpdateTypeAdapterFactory extends CustomisableTypeAdaptor<Ticket>
	{
		private TicketUpdateTypeAdapterFactory()
		{
			super(Ticket.class);
		}

		@Override
		protected void beforeWrite(Ticket ticket, JsonElement jsonTree)
		{
			jsonTree.getAsJsonObject().addProperty("type_id", ticket.getIssueType());
		}
	}
	
	*/

	
	
}
