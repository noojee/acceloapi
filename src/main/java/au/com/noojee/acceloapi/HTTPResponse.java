package au.com.noojee.acceloapi;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import au.com.noojee.acceloapi.dao.gson.GsonForAccelo;

public class HTTPResponse
{
	private static Logger logger = LogManager.getLogger();
	private int responseCode;
	private String responseMessage;
	private String responseBody;

	public HTTPResponse(int responseCode, String responseMessage, String responseBody)
	{
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseBody = responseBody;
	}

	public int getResponseCode()
	{
		return responseCode;
	}

	public String getResponseMessage()
	{
		return responseMessage;
	}

	String getResponseBody()
	{
		return responseBody;
	}

	public <T> T parseBody(Class<T> clazz) throws AcceloException
	{
		T entity;
		if (responseCode < 300)
			try
			{
				entity = GsonForAccelo.fromJson(new StringReader(this.responseBody), clazz);
			}
			catch (IllegalStateException|JsonSyntaxException e)
			{
// HttpResponse [responseCode=200, responseMessage=OK, responseBody={"response":[{"status":{"start":"no","color":"grey","standing":"closed","title":"Closed","id":"4","ordering":"0"},"type":"2","affiliation":"7237","date_closed":"1512219936","date_started":"1511960400","referrer_id":null,"resolution":"15","against_type":"company","referrer_type":null,"staff_bookmarked":"0","billable_seconds":"1800","date_due":null,"resolved_by":"1","class":"1","issue_object_budget":"14002","date_opened":"1512003115","date_last_interacted":"1512354118","date_submitted":"1512003115","assignee":"2","company":"3617","object_budget":"14002","date_resolved":"1512219936","contract":"33","standing":"closed","description":"Recieved email from Nick Waldron that mobile appointments are not flowing.\r\nCheck logs and found no errors and could see appontments arriving.\r\n\r\nmobile appiontments had not been tested as requested.\r\nResult was that the mobile appointment allocation wasn't included in the Virtual Allocation so was being ignored.\r\n\r\nModified VA to include both allocation.\r\nConfigured call scheduler to exclude old appointment requests to avoid flooding staff.\r\n\r\nConfirmed that appointments were flowing as saw staff rejecting them in the call editor.\r\n\r\n","issue_type":"2","against":"companies/3617","against_id":"3617","id":"17875","title":"After hours support - mobile appointments not flowing","closed_by":"1","custom_id":null,"resolution_detail":"Can you please confirm that full testing has occurred for mobile app appointments as well as web appointments and that the system is function as required.","submitted_by":"2","contact":"7237","priority":"2","opened_by":"2"},{"resolution_detail":"No response to emails","submitted_by":"7","contact":"8010","opened_by":"7","priority":"3","against":"companies/2712","custom_id":null,"closed_by":"7","id":"17871","title":"File format for IVR","against_id":"2712","description":"Hi Noojee Team,\r\n\r\nWe are looking at updating our IVR. What file format do the recordings need\r\nto be for us to upload?\r\n\r\n\r\nKind Regards,\r\n\r\n\r\n\r\nDamien Jones\r\nSales Manager\r\n\r\n\r\n\r\n1st Floor, 5 Yarra Street\r\nGeelong VIC 3220\r\n\r\nD   03 9021 3530\r\nM   0409 934 284\r\nP   1800 275 369\r\nE   damien@foxie.com.au\r\nW  foxie.com.au <http://www.foxie.com.au/>\r\n\r\n\r\n*This e-mail (and any attachments) is for the exclusive use of the\r\naddressee and may contain information that is privileged, confidential or\r\nprotected by copyright. If you are not the addressee or the person\r\nresponsible for delivering this e-mail to the addressee, you must not\r\ndisclose, distribute, print or copy this e-mail and the contents must be\r\nkept strictly confidential. *\r\n*If this e-mail has been sent to you in error, kindly notify me immediately\r\nby return e-mail and permanently destroy the original. *\r\n","standing":"closed","issue_type":"2","contract":"21","date_resolved":"1512368880","object_budget":"13996","company":"2712","date_submitted":"1511829888","date_last_interacted":"1511915834","assignee":"7","class":"1","resolved_by":"7","date_opened":"1511830334","issue_object_budget":"13996","referrer_type":null,"against_type":"company","resolution":null,"referrer_id":null,"date_due":"1512684000","billable_seconds":"240","staff_bookmarked":"0","affiliation":"8010","date_started":"1511830334","date_closed":"1512368880","type":"2","status":{"ordering":"0","id":"4","title":"Closed","standing":"closed","color":"grey","start":"no"}},{"date_closed":"1511960625","date_started":"1511757119","affiliation":"6930","type":"2","status":{"color":"grey","start":"no","ordering":"0","title":"Closed","id":"4","standing":"closed"},"date_opened":"1511757119","issue_object_budget":"13991","class":"1","resolved_by":"1","date_due":"1512338400","billable_seconds":"2760","staff_bookmarked":"0","referrer_type":null,"resolution":"11","against_type":"company","referrer_id":null,"description":"Hello,\r\n\r\nJust wanted to see if I could get a call looked into.\r\nIt was a call transfer the client accepted, he is saying the caller got disconnected and never spoke to them.\r\n\r\nCall id: 1511694808.740059\r\n26th/Nov 22:13:43\r\n\r\nCan we tell if the callers line got disconnected or if it is on our end?\r\n\r\nKind regards\r\n\r\n\r\n\r\nDamir Avdić\r\nClient Support & I.T Administrator\r\ndamir.avdic@tmc.net<mailto:damir.avdic@tmc.net>\r\n\r\n[http://digitalcms.com.au/signatures/tmc/img/tmc_email_logo.jpg]<http://tmc.net/>\r\n\r\n\r\nTMC 41-43 Northern Road, Heidelberg West VIC 3081<https://goo.gl/maps/ePGG1NBV6w42>\r\nT 1300 728 268<tel:1300728268> EXT 155 F +61 3 8508 9778 W www.tmc.net<http://tmc.net/>\r\n\r\n\r\n\r\nThis email and any attachments may contain privileged and confidential information and are intended for the named addressee only. If you have received this e-mail in error, please notify the sender and delete this e-mail immediately. Any confidentiality, privilege or copyright is not waived or lost because this e-mail has been sent to you in error. It is your responsibility to check this e-mail and any attachments for viruses. No warranty is made that this material is free from computer virus or any other defect or error. Any loss/damage incurred by using this material is not the sender's responsibility. The sender's entire liability will be limited to resupplying the material.\r\n\r\n\r\n\r\n\r\n","issue_type":"2","standing":"closed","contract":"3","object_budget":"13991","date_resolved":"1511960625","company":"3142","assignee":"7","date_submitted":"1511757013","date_last_interacted":"1511848198","opened_by":"7","priority":"3","contact":"6930","resolution_detail":"Review call recording and reports","submitted_by":"7","custom_id":null,"closed_by":"1","title":"Check Cause of Disconnect","id":"17867","against_id":"3142","against":"companies/3142"},{"against_id":"3697","closed_by":"7","custom_id":null,"title":"Admin Agents","id":"17865","against":"companies/3697","priority":"3","opened_by":"7","contact":"7814","submitted_by":"7","resolution_detail":"Increased admin rights on system","assignee":"7","date_last_interacted":"1511747684","date_submitted":"1511742869","date_resolved":"1511915588","object_budget":"13980","company":"3697","description":"Hi Paul,\r\n\r\nPlease we really need James Russell and Scott Atkins to be added the Admin permission so they can see all the options on the Noojee logging as  I do.\r\n\r\nJames has check t now and he still doesn't have all the options as I have and we still can not do this ourselves as we can not see the admin line under permissions.\r\n\r\nKind regards\r\n\r\nGerald\r\n","standing":"closed","issue_type":"2","contract":"94","billable_seconds":"900","staff_bookmarked":"0","date_due":"1511820000","referrer_id":null,"referrer_type":null,"resolution":"11","against_type":"company","issue_object_budget":"13980","date_opened":"1511745621","class":"1","resolved_by":"7","status":{"color":"grey","start":"no","ordering":"0","id":"4","title":"Closed","standing":"closed"},"type":"2","date_started":"1511701200","date_closed":"1511915588","affiliation":"7814"},{"contract":"44","description":"Hi Paul\r\n\r\nIt appears that when customers are calling in to the sales line some of these calls are going in to the customer service queue, can we please look in to this as all customers that press 1 on the IVR should only stay in the sales queue until they hit a voice mail.\r\n\r\nAll customers that hit option 2 should go through to Customer service.\r\n\r\nThanks\r\n\r\nWill \r\n\r\n<http://www.rockindirect.com/>\t\r\nWILLIAM BIRD\r\nCall Centre Manager\r\nROCKIN DIRECT\r\nSuites 4 & 6, 574 Plummer Street, Port Melbourne, VIC 3207\r\nPostal Address PO Box 1599, St Kilda, VIC 3192\r\nHQ +61 (0) 3 9673 4000\t•\tMobile +61 (0) 476 004 533\r\nwww.rockindirect.com <http://www.rockindirect.com/>\t•\twilliam@rockindirect.com <mailto:william@rockindirect.com>\r\n\r\nThis email and any files transmitted with are privileged and confidential information intended solely for the use of the addressee. If you have received this email in error you must not disseminate, copy or take any action in reliance on it. Please notify Rockin Direct immediately by return email to the sender and delete the original email. \r\nPlease consider the environment before printing this email and plant a few trees you may never sit under.\r\n\r\n\r\n\r\n\r\n\r\n","standing":"closed","issue_type":"2","company":"3656","object_budget":"13978","date_resolved":"1511915858","assignee":"7","date_submitted":"1511742899","date_last_interacted":"1511745021","opened_by":"7","priority":"3","submitted_by":"7","resolution_detail":"Reviewed logs and Queue setup","contact":"1091","title":"Sales Calls Coming through Customer service line","id":"17864","closed_by":"7","custom_id":null,"against_id":"3656","against":"companies/3656","date_closed":"1511915858","date_started":"1511701200","affiliation":"1091","type":"2","status":{"ordering":"0","title":"Closed","id":"4","standing":"closed","start":"no","color":"grey"},"date_opened":"1511742955","issue_object_budget":"13978","class":"1","resolved_by":"7","date_due":"1512684000","staff_bookmarked":"0","billable_seconds":"1800","against_type":"company","resolution":"11","referrer_type":null,"referrer_id":null},{"staff_bookmarked":"0","billable_seconds":"1080","date_due":"1512684000","referrer_id":null,"resolution":"11","against_type":"company","referrer_type":null,"issue_object_budget":"13977","date_opened":"1511742778","resolved_by":"1","class":"1","status":{"start":"no","color":"grey","standing":"closed","title":"Closed","id":"4","ordering":"0"},"type":"2","date_started":"1511701200","date_closed":"1512133534","affiliation":"7814","against_id":"3697","id":"17863","title":"Extended wrap Question!","closed_by":"1","custom_id":null,"against":"companies/3697","priority":"3","opened_by":"7","submitted_by":"7","contact":"7814","resolution_detail":"System updated to improve dashboard reporting",...
				logger.error("Error pasing json body. Target Class: " + clazz.getSimpleName() + " Body: " + this.responseBody);
				throw e;
			}
		else
		{
			AcceloErrorResponse error;
			try
			{
				error = GsonForAccelo.fromJson(new StringReader(this.responseBody), AcceloErrorResponse.class);
			}
			catch (Exception e)
			{
				// response body wasn't json so just create an empty error
				// object.
				error = new AcceloErrorResponse();
			}

			error.setHttpResponse(this);
			// oops something bad happened.
			throw new AcceloException(error);

		}
		return entity;

	}

	@Override
	public String toString()
	{
		return "HttpResponse [responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", responseBody="
				+ responseBody + "]";
	}

}