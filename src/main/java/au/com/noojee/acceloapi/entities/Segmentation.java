package au.com.noojee.acceloapi.entities;

public class Segmentation
{
    private String plural;

    private String singular;

    public String getPlural ()
    {
        return plural;
    }

    public void setPlural (String plural)
    {
        this.plural = plural;
    }

    public String getSingular ()
    {
        return singular;
    }

    public void setSingular (String singular)
    {
        this.singular = singular;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [plural = "+plural+", singular = "+singular+"]";
    }
}