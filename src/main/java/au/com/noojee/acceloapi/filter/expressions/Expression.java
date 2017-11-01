package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;
import java.time.ZoneId;

import au.com.noojee.acceloapi.filter.And;
import au.com.noojee.acceloapi.filter.Or;

public abstract class Expression
{
	static public LocalDate DATE1970 = LocalDate.of(1970, 1, 1);
	
	// With LocalDate's there is no easy way of passing a date value of '0'
	// 1/1/1970 gives the desired result.
	static public LocalDate DATEZERO = DATE1970;

	public abstract String toJson();

	@Override
	public abstract int hashCode();
	
	public Expression and(Expression child) // throws AcceloException
	{
		return new And(this, child);
	}

	public Expression or(Expression child) // throws AcceloException
	{
		return new Or(this, child);
	}

	// Accelo expects date filters to be a unix timestamp.
	public String formatDateAsFilterOperand(LocalDate date)
	{
		String formattedDate = "0";
		if (date != DATEZERO)
		{
			ZoneId zoneId = ZoneId.systemDefault();
			long epoch = date.atStartOfDay(zoneId).toEpochSecond();

			formattedDate = "" + epoch;
		}
		return formattedDate;
	}

	@Override
	public String toString()
	{
		return toJson() + " expression: " + hashCode();
	}

}