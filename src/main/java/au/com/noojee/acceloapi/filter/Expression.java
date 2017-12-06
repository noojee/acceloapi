package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import au.com.noojee.acceloapi.util.Constants;
import au.com.noojee.acceloapi.util.Conversions;

public abstract class Expression
{
	public abstract String toJson();

	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object e);

	public abstract Expression copy();

	// Required for serialization.
	public Expression()
	{
	}
	
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
			long epoch = Conversions.toLong(date);
			formattedDate = "" + epoch;
		}
		return formattedDate;
	}
	
	// Accelo expects date filters to be a unix timestamp.
		public String formatDateTimeAsFilterOperand(LocalDateTime dateTime)
		{
			String formattedDate = "0";
			if (dateTime != Constants.DATETIMEZERO)
			{
				long epoch = Conversions.toLong(dateTime);
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