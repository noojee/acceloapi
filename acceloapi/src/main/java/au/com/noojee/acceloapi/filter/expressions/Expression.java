package au.com.noojee.acceloapi.filter.expressions;

import java.time.LocalDate;
import java.time.ZoneId;

public interface Expression
{
	LocalDate DATE1970 = LocalDate.of(1970, 1, 1);

	public String toJson();

	// Accelo expects date filters to be a unix timestamp.
	default String formatDateAsFilterOperand(LocalDate date)
	{
		String formattedDate = "0";
		if (date != DATE1970)

		{
			ZoneId zoneId = ZoneId.systemDefault();
			long epoch = date.atStartOfDay(zoneId).toEpochSecond();

			formattedDate = "" + epoch;
		}
		return formattedDate;
	}

}