package au.com.noojee.acceloapi.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;

import au.com.noojee.acceloapi.entities.generator.BasicFilterField;
import au.com.noojee.acceloapi.entities.generator.FilterField;
import au.com.noojee.acceloapi.filter.expressions.Expression;

public abstract class AcceloEntity<E extends AcceloEntity<E>> implements Comparable<E>, Cloneable
{
	static protected CurrencyUnit currencyUnit = Monetary.getCurrency(Locale.getDefault());

	@BasicFilterField
	private int id;

	public int getId()
	{
		return id;
	}

	@Override
	public int compareTo(E rhs)
	{
		return this.id - rhs.getId();
	}

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

	/**
	 * Special - used by the AcceloCache. Don't go there.
	 * 
	 * @return
	 */
	public FilterField<E, Integer> getIdFilterField()
	{
		return new FilterField<>("id");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		AcceloEntity other = (AcceloEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}