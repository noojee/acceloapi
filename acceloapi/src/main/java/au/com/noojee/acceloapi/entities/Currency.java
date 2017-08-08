package au.com.noojee.acceloapi.entities;

public class Currency
{
    private String symbol;

    public String getSymbol ()
    {
        return symbol;
    }

    public void setSymbol (String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [symbol = "+symbol+"]";
    }
}
