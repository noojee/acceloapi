package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;

import au.com.noojee.acceloapi.Formatters;
import au.com.noojee.acceloapi.entities.meta.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.DateFilterField;

public class ContractPeriod extends AcceloEntity<ContractPeriod>
{
	@BasicFilterField
	int id;
	@BasicFilterField(name="contract")
	int contract_id;
	@DateFilterField
	long date_created;
	@DateFilterField
	long date_commenced;
	@DateFilterField
	long date_closed;
	@DateFilterField
	long date_expires;

	@BasicFilterField
	String budget_type; // pre-paid
	@BasicFilterField
	String duration_type; // fixed
	@BasicFilterField
	float contract_budget;
	@BasicFilterField
	String rollover; // yes no
	@BasicFilterField
	String standing; // open
	
	@BasicFilterField(name="rate")
	int rate_id; // -1
	
	@BasicFilterField
	String rate_type; // object
	
	@BasicFilterField
	String allowance_type; // fixed_value
	
	@BasicFilterField(name="service_item")
	int service_item_id;
	
	

	@Override
	public int getId()
	{
		return id;
	}
	
	public String getPeriodRange()
	{
		return (Formatters.format(getDateCommenced()) + " to " + Formatters.format(getDateExpires())).replace("/",  "-");
	}

	public LocalDate getDateCommenced()
	{
		return toLocalDate(date_commenced);
	}

	public LocalDate getDateExpires()
	{
		return toLocalDate(date_expires);
	}

	public double getBudget()
	{
		return this.contract_budget;
	}

	public int getContractId()
	{
		return contract_id;
	}

	public LocalDate getDateCreated()
	{
		return toLocalDate(date_created);
	}

	public LocalDate getDateClosed()
	{
		return toLocalDate(date_closed);
	}


	public String getBudget_type()
	{
		return budget_type;
	}

	public String getDuration_type()
	{
		return duration_type;
	}

	public float getContractBudget()
	{
		return contract_budget;
	}

	public String getRollover()
	{
		return rollover;
	}

	public String getStanding()
	{
		return standing;
	}

	public int getRateId()
	{
		return rate_id;
	}

	public String getRateType()
	{
		return rate_type;
	}

	public String getAllowanceType()
	{
		return allowance_type;
	}

	public int getServiceItemId()
	{
		return service_item_id;
	}

	@Override
	public String toString()
	{
		return "Contract_period [id=" + id + ", contract_id=" + contract_id + ", date_created=" + date_created
				+ ", date_commenced=" + date_commenced + ", date_closed=" + date_closed + ", date_expires="
				+ date_expires + ", budget_type=" + budget_type + ", duration_type=" + duration_type
				+ ", contract_budget=" + contract_budget + ", rollover=" + rollover + ", standing=" + standing
				+ ", rate_id=" + rate_id + ", rate_type=" + rate_type + ", allowance_type="
				+ allowance_type + ", service_item_id=" + service_item_id + "]";
	}
	
	@Override
	public int compareTo(ContractPeriod o)
	{
		return this.getDateCommenced().compareTo(o.getDateCommenced());
	}


}