package au.com.noojee.acceloapi.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

public interface Formatters
{
	static final MonetaryAmountFormat moneyFormat = MonetaryFormats.getAmountFormat(
			AmountFormatQueryBuilder.of(new Locale("AU")).set(CurrencyStyle.NAME).set("pattern", "#,##0.00").build());
	
	static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	
	static CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.getDefault());


	
	public static String format(LocalDate date)
	{
		return (date == null ? "" : date.format(dateFormat));
	}
	
	public static String format(LocalDateTime dateTime)
	{
		return (dateTime == null ? "" : dateTime.format(dateFormat));
	}

	
	/**
	 * Formats the Duration to H:mm
	 * 
	 * @param duration
	 * @return a blank string if duration is null otherwise the duration as per the format.
	 */
	public static String format(Duration duration)
	{

		return (duration == null ? "" : DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm"));
	}

	/**
	 * Formats the Duration to the given format.
	 * Formats support are any supported by {@link DurationFormatUtils.formatDuration}
	 * @param duration
	 * @param format to render duration to.
	 * @return a blank string if duration is null otherwise the duration as per the format.
	 */
	public static String format(Duration duration, String format)
	{

		return (duration == null ? "" : DurationFormatUtils.formatDuration(duration.toMillis(), format));
	}

	public static String format(Money amount)
	{
		return moneyFormat.format(amount);
	}
	public static CurrencyUnit getCurrency()
	{
		return currencyUnit;
	}

}
