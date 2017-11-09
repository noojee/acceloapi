package au.com.noojee.acceloapi.entities.meta;

public enum AgainstType_
{
	company, contact, issue,ticket("issue")

	, affiliation, annex, campaign, account_invoice, campaign_action, component, contract
	, contract_period, deployment, event, invoice, job, membership, prospect, request, task, staff, activities;
	
	
	private String _name;
	
	AgainstType_()
	{
		this._name = name();
	}

	AgainstType_(String name)
	{
		this._name = name;
	}
	
	
	public String getName()
	{
		return this._name;
	}

}