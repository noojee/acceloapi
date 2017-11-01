package au.com.noojee.acceloapi.entities;


public class Locale
{
private Currency currency;

public Currency getCurrency ()
{
return currency;
}

public void setCurrency (Currency currency)
{
this.currency = currency;
}

@Override
public String toString()
{
return "ClassPojo [currency = "+currency+"]";
}
}
