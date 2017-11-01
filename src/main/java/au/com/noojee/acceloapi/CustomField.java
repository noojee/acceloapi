package au.com.noojee.acceloapi;

// Class for holding custom Accelo fields
public class CustomField
{
	String value_type;
	String id;
	private String field_name;
	String field_type;
	private String value ;
	@Override
	public String toString()
	{
		return "CustomField [value_type=" + value_type + ", id=" + id + ", field_name=" + getName() + ", field_type="
				+ field_type + ", value=" + getValue() + "]";
	}
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
}