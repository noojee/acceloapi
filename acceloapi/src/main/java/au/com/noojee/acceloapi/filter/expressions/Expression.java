package au.com.noojee.acceloapi.filter.expressions;

import java.util.Date;

public interface Expression
{
	public String toJson();

	// Accelo expects date filters to be a unix timestamp.
	default String formatDateAsFilterOperand(Date date)
	{
		return "" + date.getTime() / 1000;
	}

}