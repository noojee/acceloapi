package au.com.noojee.acceloapi.entities.types;

import au.com.noojee.acceloapi.entities.Ticket.Standing;

public class TicketStatus
{
	/** This shouldn't be here. Need to move it elsewhere **/
	/*
	enum NoojeeTicketEnums
	{
	@SerializedName("2")
	Open(2),
	@SerializedName("4")
	Closed(4),
	@SerializedName("9")
	BillingServiceTerminated(9),
	@SerializedName("12")
	SendToDebtCollector(12),
	@SerializedName("6")
	Waiting(6),
	@SerializedName("10")
	AwaitingOrionSuspension(10),
	@SerializedName("14")
	ASICReportAttached(14),
	@SerializedName("13")
	FinalInvoiceGenerated(13),
	@SerializedName("15")
	NewDIDNeeded(15),
	@SerializedName("16")
	DIDAllocated(16),
	@SerializedName("17")
	DIDInfoNeeded(17),
	@SerializedName("18")
	NewDIDOrdered(18),
	@SerializedName("19")
	AllocatingDIDFromPool(19),
	@SerializedName("38")
	ConfigurationComplete(38),
	@SerializedName("20")
	One300DIDSetup(20),
	@SerializedName("21")
	InProgress(21),
	@SerializedName("22")
	ClientConfirmationNeeded(22),
	@SerializedName("23")
	InDevelopment(23),
	@SerializedName("24")
	WaitingOnClient(24),
	@SerializedName("25")
	WaitingForSignoff(25),
	@SerializedName("26")
	InReview(26),
	@SerializedName("27")
	ClientReportedIssue(27),
	@SerializedName("28")
	InternallyIdentifiedIssue(28),
	@SerializedName("31")
	BillingMOdificiationPending(31),
	@SerializedName("32")
	ClientApproved(32),
	@SerializedName("33")
	DebtRecovered(33),
	@SerializedName("34")
	WriteOffDebt(34),
	@SerializedName("35")
	One300NoOrdered(35),
	@SerializedName("36")
	AwiatingAvail1300List(36),
	@SerializedName("42")
	IAHImportInactive(42),
	@SerializedName("39")
	CancellationConfirmed(39),
	@SerializedName("40")
	AwaitingPortCompletion(40),
	@SerializedName("41")
	InTesting(41),
	@SerializedName("43")
	CreateCreditNote(43),
	@SerializedName("44")
	ConfigurationPending(44);
	};
	*/
		
	 
		
	private int id; 						// A unique identifier for the status.
	private String title; 			// A title for the status.
	private String color; 			// The color the status will appear in the Accelo deployment.
	private Standing standing;	// The standing of the status. For example “active”, “paused”.
	private String start; 			// Either “yes” or “no”, whether this status is available upon creation of the object.
	int ordering;						// A number representing the status’ order on the Accelo deployment.
	
	/*
	TicketStatus(int id)
	{
		this.id = id;
		
	
		 String[] words = this.name().split("(?<!^)(?=[A-Z])");
		
		this.title = "";
		for (String word : words)
		{
			if (title.length() > 0)
				title += " ";
			this.title += word;
		}
		
	}
	*/
	
	

	public String getTitle()
	{
		return title;
	}
	
	public Standing getStanding()
	{
		return standing;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "TicketStatus [id=" + id + ", title=" + title + ", color=" + color + ", standing=" + standing
				+ ", start=" + start + ", ordering=" + ordering + "]";
	}


	
	
}
