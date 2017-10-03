# acceloapi
Java api for the Accelo CRM

As they name says this is an Java API that makes it easier to talk to the Accelo REST API.

Examples:

    // get an instance of the api.
    AcceloApi acceloApi = new AcceloApi();

    // initialise it.
    AcceloApi.setFQDN("<youraccelohostname>.api.accelo.com");
    AcceloApi.setClientID("<your accelo client id>);
    AcceloApi.setClientSecret("<your accelo client secret>");

    // Now connect. You can use this single connect throughout your code.
    acceloApi.connect();

    // get a company by name
    String companyName = "Some company name";
    Company company = new CompanyDao().getByName(acceloApi, companyName);

    // Get the Retainer contract
    Contract contract = new ContractDao().getActiveContract(acceloApi, company);

    // Get a list of contratc periods for the retainer.
    List<ContractPeriod> periods = new ContractPeriodDao().getContractPeriods(acceloApi, contract);

    // Get a list of tickets attached to the contract.
    List<Ticket> tickets = new TicketDao().getByContract(acceloApi, contract);

    // Get a ticket by its no.
    Ticket ticket = new TicketDao().getById(acceloApi, 123);

    // Get a Staff member by their email
    Staff staff = new StaffDao().getByEmail(acceloApi, "staffmember@myorg.com.au");

    // Get a contact by their name
    Contact contact = new ContactDao().getContact(acceloApi, "firstname", "lastname");
	
	

	The API tries to use Json for filters/sending and recieving data. 
	To help creating json filters there is a small set of classes that try to make this easier. 
	Currenlty they don't handle every possible fitler combination:

	Create a fitler to match on a Staff email address:

	// Create a filter
	AcceloFilter filter = new AcceloFilter();
	filter.add(new Eq("email", staffEmailAddress));

	// Use the universal getByFitler
	List<Staff> = new StaffDao().getByFilter(acceloApi, filter);

	// get a staff member by id
	AcceloFilter filter = new AcceloFilter();
	filter.add(new Eq("id", staff_id));
	List<Staff> = new StaffDao().getByFilter(acceloApi, filter);

	// Use the accelo 'search' filter get get a company by name:
	AcceloFilter filter = new AcceloFilter();
	filter.add(new Search(companyName));
	List<Company> = new CompanyDao().getByFilter(acceloApi, filter);

	// Build a compound filter
	AcceloFilter filters = new AcceloFilter();

	// Filter using the against field with a type of company and an company id.
	filters.add(new AcceloFilter.Compound("against"))
	.add(new Eq("company", company.getId()));
	List<Company> = new CompanyDao().getByFilter(acceloApi, filter);

	// Search for tickets by company and contact id
	AcceloFilter filter = new AcceloFilter();
	filter.add(new Eq("company_id", companyId))
	.add(new Eq("contact_id", contactId));
	List<Ticket> = new TicketDao().getByFilter(acceloApi, filter);







            

	






    
    
    
    
    
