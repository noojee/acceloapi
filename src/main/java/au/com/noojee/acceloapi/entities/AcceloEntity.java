package au.com.noojee.acceloapi.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;

import au.com.noojee.acceloapi.entities.meta.FilterField;
import au.com.noojee.acceloapi.filter.expressions.Expression;

public abstract class AcceloEntity<E extends AcceloEntity<E>> implements Comparable<E>
{
	static protected CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.getDefault());

	public abstract int getId();
	
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

	public static long toDateAsLong(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
	}

	public static Date toDate(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Special - used by the AcceloCache. Don't go there.
	 * @return
	 */
	public FilterField<E, Integer> getIdFilterField()
	{
		return new FilterField<>("id");
	}
}