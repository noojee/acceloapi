package au.com.noojee.acceloapi.util;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;


public interface Constants
{
	static public final  Locale LOCAL =Locale.getDefault();
	static public final CurrencyUnit LOCAL_CURRENCY = CurrencyUnit.AUD;

	static public LocalDate DATE1970 = LocalDate.of(1970, 1, 1);
	
	static public LocalDateTime DATETIME1970 = LocalDateTime.of(1970, 1, 1,0,0,0);
	
	// With LocalDate's there is no easy way of passing a date value of '0'
	// 1/1/1970 gives the desired result.
	static public LocalDate DATEZERO = DATE1970;
	static public LocalDateTime DATETIMEZERO = DATETIME1970;
	
	static public Money MONEY_ZERO = Money.zero(LOCAL_CURRENCY);

	public static final Money MONEY_ONE_DOLLAR = Money.of(LOCAL_CURRENCY, new BigDecimal(1));

	
	static public Duration FIFTEEN_MINUTES = Duration.ofMinutes(15);

	public static String NJ_EMAIL_ACCOUNTS = "accounts@noojee.com.au";

	public static String NJ_EMAIL_SALES = "sales@noojee.com.au";
	
	public static String NJ_EMAIL_SUPPORT = "support@noojee.com.au";

	public static String NJ_EMAIL_FROM = "auditor@noojee.com.au";
	
	public static String NJ_EMAIL_DEBT_COLLECTOR = "debtcoll@noojee.com.au";
	
	
}
