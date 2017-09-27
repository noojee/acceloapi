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




    
    
    
    
    
