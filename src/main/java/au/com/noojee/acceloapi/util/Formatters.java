package au.com.noojee.acceloapi.util;

import java.time.Duration;
import java.time.LocalDate;
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

public class Formatters
{

	private static final MonetaryAmountFormat moneyFormat = MonetaryFormats.getAmountFormat(
			AmountFormatQueryBuilder.of(new Locale("AU")).set(CurrencyStyle.NAME).set("pattern", "#,##0.00").build());
	
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	
	private static CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.getDefault());


	
	public static String format(LocalDate date)
	{
		return (date == null ? "" : date.format(dateFormat));
	}
	public static String format(Duration duration)
	{

		return (duration == null ? "" : DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm"));
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
