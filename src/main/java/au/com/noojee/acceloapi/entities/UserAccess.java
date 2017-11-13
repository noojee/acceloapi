package au.com.noojee.acceloapi.entities;

public class UserAccess
{
    private Asset asset;

    private Contract contract;

    private Task task;

    private Invoice invoice;

    private Ticket issue;

    private Resource resource;

    private Prospect prospect;

    private Job job;

    private Contact contact;

    private Milestone milestone;

    private Division division;

    private Expense expense;

    private Quote quote;

    private Timer timer;

    private Request request;

    private Company company;

    private AccountInvoice account_invoice;

    private Campaign campaign;

    private Activity activity;

    private ContractPeriod contract_period;

    public Asset getAsset ()
    {
        return asset;
    }

    public void setAsset (Asset asset)
    {
        this.asset = asset;
    }

    public Contract getContract ()
    {
        return contract;
    }

    public void setContract (Contract contract)
    {
        this.contract = contract;
    }

    public Task getTask ()
    {
        return task;
    }

    public void setTask (Task task)
    {
        this.task = task;
    }

    public Invoice getInvoice ()
    {
        return invoice;
    }

    public void setInvoice (Invoice invoice)
    {
        this.invoice = invoice;
    }

    public Ticket getIssue ()
    {
        return issue;
    }

    public void setIssue (Ticket issue)
    {
        this.issue = issue;
    }

    public Resource getResource ()
    {
        return resource;
    }

    public void setResource (Resource resource)
    {
        this.resource = resource;
    }

    public Prospect getProspect ()
    {
        return prospect;
    }

    public void setProspect (Prospect prospect)
    {
        this.prospect = prospect;
    }

    public Job getJob ()
    {
        return job;
    }

    public void setJob (Job job)
    {
        this.job = job;
    }

    public Contact getContact ()
    {
        return contact;
    }

    public void setContact (Contact contact)
    {
        this.contact = contact;
    }

    public Milestone getMilestone ()
    {
        return milestone;
    }

    public void setMilestone (Milestone milestone)
    {
        this.milestone = milestone;
    }

    public Division getDivision ()
    {
        return division;
    }

    public void setDivision (Division division)
    {
        this.division = division;
    }

    public Expense getExpense ()
    {
        return expense;
    }

    public void setExpense (Expense expense)
    {
        this.expense = expense;
    }

    public Quote getQuote ()
    {
        return quote;
    }

    public void setQuote (Quote quote)
    {
        this.quote = quote;
    }

    public Timer getTimer ()
    {
        return timer;
    }

    public void setTimer (Timer timer)
    {
        this.timer = timer;
    }

    public Request getRequest ()
    {
        return request;
    }

    public void setRequest (Request request)
    {
        this.request = request;
    }

    public Company getCompany ()
    {
        return company;
    }

    public void setCompany (Company company)
    {
        this.company = company;
    }

    public AccountInvoice getAccount_invoice ()
    {
        return account_invoice;
    }

    public void setAccount_invoice (AccountInvoice account_invoice)
    {
        this.account_invoice = account_invoice;
    }

    public Campaign getCampaign ()
    {
        return campaign;
    }

    public void setCampaign (Campaign campaign)
    {
        this.campaign = campaign;
    }

    public Activity getActivity ()
    {
        return activity;
    }

    public void setActivity (Activity activity)
    {
        this.activity = activity;
    }

    public ContractPeriod getContract_period ()
    {
        return contract_period;
    }

    public void setContract_period (ContractPeriod contract_period)
    {
        this.contract_period = contract_period;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [asset = "+asset+", contract = "+contract+", task = "+task+", invoice = "+invoice+", issue = "+issue+", resource = "+resource+", prospect = "+prospect+", job = "+job+", contact = "+contact+", milestone = "+milestone+", division = "+division+", expense = "+expense+", quote = "+quote+", timer = "+timer+", request = "+request+", company = "+company+", account_invoice = "+account_invoice+", campaign = "+campaign+", activity = "+activity+", contract_period = "+contract_period+"]";
    }
}