package au.com.noojee.acceloapi.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.money.Money;

public interface Format
{

	static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	static final DateTimeFormatter saasuDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static final DateTimeFormatter dateWithMonthFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

	static final DateTimeFormatter dateFormatTime = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mma");

	static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");

	public static String format(LocalDate date)
	{
		return (date == null ? "" : date.format(dateFormat));
	}

	public static String formatWithMonth(LocalDate date)
	{
		return (date == null ? "" : date.format(dateWithMonthFormat));
	}

	public static String format(LocalDateTime dateTime)
	{
		return (dateTime == null ? "" : dateTime.format(dateFormatTime));
	}

	public static String format(LocalTime time)
	{
		return (time == null ? "" : time.format(timeFormat));
	}

	public static String saasuFormat(LocalDate date)
	{
		return (date == null ? "" : date.format(saasuDateFormat));
	}

	/**
	 * Formats the Duration to H:mm
	 * 
	 * @param duration
	 * @return a blank string if duration is null otherwise the duration as per the format.
	 */
	public static String format(Duration duration)
	{

		if (duration == null)
			return "";
		if (duration.toMillis() >= 60000)
			return DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm");
		return DurationFormatUtils.formatDuration(duration.toMillis(), "s") + " secs";

	}

	/**
	 * Formats the Duration to the given format. Formats support are any supported by
	 * {@link DurationFormatUtils.formatDuration}
	 * 
	 * @param duration
	 * @param format to render duration to.
	 * @return a blank string if duration is null otherwise the duration as per the format.
	 */
	public static String format(Duration duration, String format)
	{

		return (duration == null ? "" : DurationFormatUtils.formatDuration(duration.toMillis(), format));
	}

	public static String format(Money money, boolean showDollarSign)
	{
		String prefix = "";
		if (showDollarSign)
			prefix = "$";
		if (money != null)
		{
			long cents = money.multipliedBy(100).getAmountMajorLong();

			return String.format(prefix + "%,1d.%02d", cents / 100, Math.abs(cents) % 100);
		}
		return prefix + "0.00";

	}

	public static String format(Money money)
	{
		return format(money, false);
	}

	/**
	 * No delimiters and no currency.
	 * 
	 * @param money
	 * @return
	 */
	public static String formatSimple(Money money)
	{
		if (money != null)
		{
			long cents = money.multipliedBy(100).getAmountMajorLong();

			return String.format("%1d.%02d", cents / 100, Math.abs(cents) % 100);
		}
		return "0.00";
	}

}
