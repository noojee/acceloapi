package au.com.noojee.acceloapi.entities;

public class Accelo
{
    private String deployment_uri;

    private String account_id;

    // private Account_details account_details;

    private String expires_in;

    private String token_type;

    private String deployment;

    private String deployment_name;

    private String access_token;

    public String getDeployment_uri ()
    {
        return deployment_uri;
    }

    public void setDeployment_uri (String deployment_uri)
    {
        this.deployment_uri = deployment_uri;
    }

    public String getAccount_id ()
    {
        return account_id;
    }

    public void setAccount_id (String account_id)
    {
        this.account_id = account_id;
    }
/*
    public Account_details getAccount_details ()
    {
        return account_details;
    }

    public void setAccount_details (Account_details account_details)
    {
        this.account_details = account_details;
    }
*/

    public String getExpires_in ()
    {
        return expires_in;
    }

    public void setExpires_in (String expires_in)
    {
        this.expires_in = expires_in;
    }

    public String getToken_type ()
    {
        return token_type;
    }

    public void setToken_type (String token_type)
    {
        this.token_type = token_type;
    }

    public String getDeployment ()
    {
        return deployment;
    }

    public void setDeployment (String deployment)
    {
        this.deployment = deployment;
    }

    public String getDeployment_name ()
    {
        return deployment_name;
    }

    public void setDeployment_name (String deployment_name)
    {
        this.deployment_name = deployment_name;
    }

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

      @Override
    public String toString()
    {
        return "ClassPojo [deployment_uri = "+deployment_uri+", account_id = "+account_id
        // , account_details = "+account_details+"
        +", expires_in = "+expires_in+", token_type = "+token_type+", deployment = "+deployment+", deployment_name = "+deployment_name+", access_token = "+access_token+"]";
    }
}
