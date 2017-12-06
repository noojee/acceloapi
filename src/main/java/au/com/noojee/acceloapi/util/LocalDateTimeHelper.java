package au.com.noojee.acceloapi.util;

import java.time.LocalDateTime;

public class LocalDateTimeHelper
{
	public static LocalDateTime Max(LocalDateTime lhs, LocalDateTime rhs)
	{
		if (lhs == null)
			return rhs;
		if (rhs == null)
			return lhs;
		
		if (lhs.isAfter(rhs))
				return lhs;
		else
			return rhs;
	}
	
	public static LocalDateTime Min(LocalDateTime lhs, LocalDateTime rhs)
	{
		if (lhs == null)
			return rhs;
		if (rhs == null)
			return lhs;

		if (lhs.isBefore(rhs))
				return lhs;
		else
			return rhs;
	}
}
