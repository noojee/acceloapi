package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;
import java.time.ZoneId;

import au.com.noojee.acceloapi.util.Constants;

public abstract class Expression
{
	public abstract String toJson();

	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object e);

	
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
		if (date != Constants.DATEZERO)
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