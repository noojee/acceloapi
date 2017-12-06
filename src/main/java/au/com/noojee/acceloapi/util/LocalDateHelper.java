package au.com.noojee.acceloapi.util;

import java.time.LocalDate;

public class LocalDateHelper
{
	public static LocalDate Max(LocalDate lhs, LocalDate rhs)
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
	
	public static LocalDate Min(LocalDate lhs, LocalDate rhs)
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
