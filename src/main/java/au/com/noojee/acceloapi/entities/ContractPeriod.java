package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import au.com.noojee.acceloapi.entities.meta.fieldTypes.BasicFilterField;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.DateFilterField;
import au.com.noojee.acceloapi.util.Formatters;

public class ContractPeriod extends AcceloEntity<ContractPeriod>
{
	@BasicFilterField(name = "contract")
	private int contract_id;
	@DateFilterField
	private LocalDateTime date_created ;
	@DateFilterField
	private LocalDate date_commenced ;
	@DateFilterField
	private LocalDate date_closed ;
	@DateFilterField
	private LocalDate date_expires ;

	@BasicFilterField
	private String budget_type; // pre-paid
	@BasicFilterField
	private String duration_type; // fixed
	@BasicFilterField
	private float contract_budget;
	@BasicFilterField
	private String rollover; // yes no
	@BasicFilterField
	private String standing; // open

	@BasicFilterField(name = "rate")
	private int rate_id; // -1

	@BasicFilterField
	private String rate_type; // object

	@BasicFilterField
	private String allowance_type; // fixed_value

	@BasicFilterField(name = "service_item")
	private int service_item_id;

	public String getPeriodRange()
	{
		return (Formatters.format(getDateCommenced()) + " to " + Formatters.format(getDateExpires())).replace("/", "-");
	}

	public LocalDate getDateCommenced()
	{
		return date_commenced;
	}

	public LocalDate getDateExpires()
	{
		return date_expires;
	}

	public double getBudget()
	{
		return this.contract_budget;
	}

	public int getContractId()
	{
		return contract_id;
	}

	public LocalDateTime getDateTimeCreated()
	{
		return date_created;
	}

	public LocalDate getDateClosed()
	{
		return date_closed;
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
		return "Contract_period [id=" + getId() + ", contract_id=" + contract_id + ", date_created=" + date_created
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