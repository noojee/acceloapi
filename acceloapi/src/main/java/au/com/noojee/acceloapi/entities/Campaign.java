package au.com.noojee.acceloapi.entities;
public class Campaign
{
    private String manages;

    private String admin;

    private String view;

    private String dashboard;

    private String add;

    public String getManages ()
    {
        return manages;
    }

    public void setManages (String manages)
    {
        this.manages = manages;
    }

    public String getAdmin ()
    {
        return admin;
    }

    public void setAdmin (String admin)
    {
        this.admin = admin;
    }

    public String getView ()
    {
        return view;
    }

    public void setView (String view)
    {
        this.view = view;
    }

    public String getDashboard ()
    {
        return dashboard;
    }

    public void setDashboard (String dashboard)
    {
        this.dashboard = dashboard;
    }

    public String getAdd ()
    {
        return add;
    }

    public void setAdd (String add)
    {
        this.add = add;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [manages = "+manages+", admin = "+admin+", view = "+view+", dashboard = "+dashboard+", add = "+add+"]";
    }
}
			
			