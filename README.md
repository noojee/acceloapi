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
    Company company = Company.getByName(acceloApi, companyName);

    // Get the Retainer contract
    Contract contract = Contract.getActiveContract(acceloApi, company);

    // Get a list of contratc periods for the retainer.
    List<ContractPeriod> periods = contract.getContractPeriods(acceloApi);

    // Get a list of tickets attached to the contract.
    List<Ticket> tickets = Ticket.getByContract(acceloApi, contract);

    // Get a ticket by its no.
    Ticket ticket = Ticket.getByTicketNo(acceloApi, 123);

    // Get a Staff member by their email
    Staff staff = Staff.getByEmail(acceloApi, "staffmember@myorg.com.au");

    // Get a contact by their name
    Contact contact = Contact.getContact(acceloApi, "firstname", "lastname");


	The API tries to use Json for filters/sending and recieving data. 
	To add creating json filters there is a small set of classes that try to make this easier. 
	Currenlty they don't handle every possible fitler combination:

	Create a fitler to match on a Staff email address:

	// Create a filter
	AcceloFilter filter = new AcceloFilter();
	filter.add(new AcceloFilter.SimpleMatch("email", staffEmailAddress));

	// Pass it to the pull method 
	response = acceloApi.pull(AcceloApi.HTTPMethod.GET, AcceloApi.EndPoints.staff.getURL(), filter,
			AcceloFieldList.ALL, Staff.ResponseList.class);

	// get a staff member by id
	AcceloFilter filter = new AcceloFilter();
	filter.add(new AcceloFilter.SimpleMatch("id", staff_id));

	// Use the accelo 'search' filter get get a company by name:
	AcceloFilter filter = new AcceloFilter();
	filter.add(new Search(companyName));

	// Build a compound filter
	AcceloFilter filters = new AcceloFilter();

	// Filter using the against field with a type of company and an company id.
	filters.add(new AcceloFilter.CompoundMatch("against"))
	.add(new AcceloFilter.SimpleMatch("company", company.getId()));

	// Search by company and contact id
	AcceloFilter filter = new AcceloFilter();
	filter.add(new SimpleMatch("company_id", companyId))
	.add(new SimpleMatch("contact_id", contactId));







            

	






    
    
    
    
    
