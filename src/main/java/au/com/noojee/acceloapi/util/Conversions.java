package au.com.noojee.acceloapi.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;

import au.com.noojee.acceloapi.filter.expressions.Expression;

public class Conversions
{
	static protected CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.getDefault());

	public static Money asMoney(double value)
	{
		return Money.of(value, currencyUnit);
	}

	public static double asDouble(Money value)
	{
		return value.getNumber().doubleValue();
	}

	public static LocalDate toLocalDate(long dateToSeconds)
	{
		LocalDate localDate = Instant.ofEpochSecond(dateToSeconds).atZone(ZoneId.systemDefault()).toLocalDate();

		return (localDate.equals(Expression.DATEZERO) ? null : localDate);
	}

	public static long toLong(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
	}

	public static Date toDate(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
