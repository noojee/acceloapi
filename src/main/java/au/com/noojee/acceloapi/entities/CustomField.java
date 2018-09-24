package au.com.noojee.acceloapi.entities;

// Class for holding custom Accelo fields
public class CustomField extends AcceloEntity<CustomField>
{
	private String value_type;
	private String field_name;
	private String field_type;
	private String value;

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getName()
	{
		return field_name;
	}

	public void setName(String field_name)
	{
		this.field_name = field_name;
	}

	@Override
	public int compareTo(CustomField o)
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return "CustomField [value_type=" + value_type + ", id=" + getId() + ", field_name=" + getName()
				+ ", field_type="
				+ field_type + ", value=" + getValue() + "]";
	}

}