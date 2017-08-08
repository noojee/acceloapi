package au.com.noojee.acceloapi;

// Class for holding custom fields
public class CustomField
{
	String value_type;
	String id;
	String field_name;
	String field_type;
	String value ;
	@Override
	public String toString()
	{
		return "CustomField [value_type=" + value_type + ", id=" + id + ", field_name=" + field_name + ", field_type="
				+ field_type + ", value=" + value + "]";
	}
}