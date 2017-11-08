package au.com.noojee.acceloapi.entities.meta;

public enum AgainstType_
{
	company, contact, issue,ticket("issue")

	, affiliation, annex, campaign, account_invoice, campaign_action, component, contract
	, contract_period, deployment, event, invoice, job, membership, prospect, request, task, staff, activities;
	
	
	private String name;
	
	AgainstType_()
	{
		this.name = name();
	}

	AgainstType_(String name)
	{
		this.name = name;
	}
	
	
	public String getName()
	{
		return this.name();
	}

}