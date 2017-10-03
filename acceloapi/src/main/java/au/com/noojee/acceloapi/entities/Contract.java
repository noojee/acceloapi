package au.com.noojee.acceloapi.entities;

import java.time.LocalDate;

import au.com.noojee.acceloapi.AcceloApi;

public class Contract extends AcceloEntity<Contract>
{
	int id;
	String company;			// The owning company id.
	String title;
	long date_created;
	long date_started;
	long date_period_expires;
	long date_expires;
	long date_last_interacted;
	int renew_days;
	String standing;
	String auto_renew;
	String deployment;
	String against;

	String against_type;
	String manager;
	String job;
	double value;
	String status;
	int billable;
	String send_invoice;
	String staff_bookmarked;
	int type;
	int against_id;
	String notes;
	int period_template_id;

	
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
	public double getValue()
	{
		return value;
	}

	public LocalDate getDateCreated()
	{
		return AcceloApi.toLocalDate(date_created);
	}

	public LocalDate getDateStarted()
	{
		return AcceloApi.toLocalDate(date_started);
	}

	public LocalDate getDatePeriodExpires()
	{
		return AcceloApi.toLocalDate(date_period_expires);
	}

	public LocalDate getDateExpires()
	{
		return AcceloApi.toLocalDate(date_expires);
	}

	public LocalDate getDateLastInteracted()
	{
		return AcceloApi.toLocalDate(date_last_interacted);
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

	public String getAgainst()
	{
		return against;
	}

	public String getAgainstType()
	{
		return against_type;
	}

	public String getManager()
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
				+ ", standing=" + standing + ", auto_renew=" + auto_renew + ", deployment=" + deployment + ", against="
				+ against + ", against_type=" + against_type + ", manager=" + manager + ", job=" + job + ", value="
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

}
