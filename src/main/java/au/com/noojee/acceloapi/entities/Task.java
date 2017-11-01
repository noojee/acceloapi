package au.com.noojee.acceloapi.entities;
public class Task
{
    private String manages;

    private String admin;

    private String view;

    private String add;

    private String dashboard;

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

    public String getAdd ()
    {
        return add;
    }

    public void setAdd (String add)
    {
        this.add = add;
    }

    public String getDashboard ()
    {
        return dashboard;
    }

    public void setDashboard (String dashboard)
    {
        this.dashboard = dashboard;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [manages = "+manages+", admin = "+admin+", view = "+view+", add = "+add+", dashboard = "+dashboard+"]";
    }
}
			
			