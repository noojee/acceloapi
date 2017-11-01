package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;

import org.javamoney.moneta.Money;

import au.com.noojee.acceloapi.entities.meta.AcceloField;
import au.com.noojee.acceloapi.entities.meta.AcceloField.Type;

public class Contract extends AcceloEntity<Contract> 
{
	@AcceloField
	private int id;
	private int company;			// The owning company id.
	private String title;
	@AcceloField(Type.DATE)
	private long date_created;
	@AcceloField(Type.DATE)
	private long date_started;
	@AcceloField(Type.DATE)
	private long date_period_expires;
	@AcceloField(Type.DATE)
	private long date_expires;
	private long date_last_interacted;
	private int renew_days;
	@AcceloField
	private String standing;
	@AcceloField
	private String auto_renew;
	private String deployment;
	private int against_id;
	private String against_type;
	@AcceloField
	private int manager;  // the staff id of the staff member that manages this contract.
	private String job;
	private double value;
	@AcceloField
	private String status;
	private int billable;
	private String send_invoice;
	private String staff_bookmarked;
	@AcceloField
	private int type;
	private String notes;
	private int period_template_id;
	@AcceloField
	private int owner_affiliation;
	@AcceloField
	private int billable_affiliation;
	
	@Override
	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}
	
	/**
	 * The dollar amount this contract includes in value
	 * 
	 * @return
	 */
	public Money getValue()
	{
		return asMoney(value);
	}
	
	public Money getRemainingValue()
	{
		// until we work out how to get this.
		return asMoney(0);
	}


	public LocalDate getDateCreated()
	{
		return toLocalDate(date_created);
	}

	public LocalDate getDateStarted()
	{
		return super.toLocalDate(date_started);
	}

	public LocalDate getDatePeriodExpires()
	{
		return toLocalDate(date_period_expires);
	}

	public LocalDate getDateExpires()
	{
		return super.toLocalDate(date_expires);
	}

	public LocalDate getDateLastInteracted()
	{
		return super.toLocalDate(date_last_interacted);
	}

	public int getRenewDays()
	{
		return renew_days;
	}

	public String getStanding()
	{
		return standing;
	}

	public String getAutoRenew()
	{
		return auto_renew;
	}

	public String getDeployment()
	{
		return deployment;
	}

	public int getOwnerAffiliation()
	{
		return owner_affiliation;
	}

	public int getBillableAffiliation()
	{
		return billable_affiliation;
	}

	public String getAgainstType()
	{
		return against_type;
	}

	public int getManager()
	{
		return manager;
	}

	public String getJob()
	{
		return job;
	}

	public String getStatus()
	{
		return status;
	}

	public int getBillable()
	{
		return billable;
	}

	public String getSendInvoice()
	{
		return send_invoice;
	}

	public String getStaffBookmarked()
	{
		return staff_bookmarked;
	}

	public int getType()
	{
		return type;
	}

	public int getAgainstId()
	{
		return against_id;
	}

	public String getNotes()
	{
		return notes;
	}

	public int getPeriodTemplateId()
	{
		return period_template_id;
	}

	public int getCompanyId()
	{
		return Integer.valueOf(company);
	}

	@Override
	public String toString()
	{
		return "Contract [id=" + id + ", company=" + company + ", title=" + title + ", date_created=" + date_created
				+ ", date_started=" + date_started + ", date_period_expires=" + date_period_expires + ", date_expires="
				+ date_expires + ", date_last_interacted=" + date_last_interacted + ", renew_days=" + renew_days
				+ ", standing=" + standing + ", auto_renew=" + auto_renew + ", deployment=" + deployment + ", owner_affiliation="
				+ owner_affiliation + ", against_type=" + against_type + ", manager=" + manager + ", job=" + job + ", value="
				+ value + ", status=" + status + ", billable=" + billable + ", send_invoice=" + send_invoice
				+ ", staff_bookmarked=" + staff_bookmarked + ", type=" + type + ", against_id=" + against_id
				+ ", notes=" + notes + ", period_template_id=" + period_template_id + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	@Override
	public int compareTo(Contract o)
	{
		return this.getDateStarted().compareTo(o.getDateStarted());
	}


}
