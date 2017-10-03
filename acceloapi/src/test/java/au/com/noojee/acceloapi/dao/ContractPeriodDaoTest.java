package au.com.noojee.acceloapi.dao;

import java.util.List;

import org.junit.Test;

import au.com.noojee.acceloapi.entities.ContractPeriod;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.HTTPResponse;

public class ContractPeriodDaoTest
{

	@Test
	public void test()
	{
		String sourceJson = "{\"response\":{\"periods\":[{\"contract\":\"contracts/44\",\"duration_type\":\"fixed\",\"date_created\":\"1505195569\",\"id\":\"282\",\"rollover\":\"no\",\"allowance_type\":\"fixed_value\",\"rate_id\":\"18\",\"rate_type\":\"object\",\"service_item_id\":\"381\",\"budget_type\":\"pre-paid\",\"date_commenced\":\"1488330000\",\"date_closed\":null,\"contract_budget\":\"281\",\"standing\":\"opened\",\"date_expires\":\"1490922000\",\"contract_id\":\"44\"},{\"contract_budget\":\"282\",\"standing\":\"opened\",\"date_expires\":\"1493517600\",\"contract_id\":\"44\",\"date_commenced\":\"1491008400\",\"date_closed\":null,\"rate_type\":\"object\",\"service_item_id\":\"382\",\"budget_type\":\"pre-paid\",\"rate_id\":\"18\",\"allowance_type\":\"fixed_value\",\"date_created\":\"1505195780\",\"rollover\":\"no\",\"id\":\"283\",\"duration_type\":\"fixed\",\"contract\":\"contracts/44\"},{\"contract\":\"contracts/44\",\"duration_type\":\"fixed\",\"rollover\":\"no\",\"id\":\"284\",\"date_created\":\"1505195952\",\"allowance_type\":\"fixed_value\",\"rate_id\":\"18\",\"rate_type\":\"object\",\"service_item_id\":\"383\",\"budget_type\":\"pre-paid\",\"date_closed\":null,\"date_commenced\":\"1493604000\",\"date_expires\":\"1496196000\",\"contract_id\":\"44\",\"contract_budget\":\"283\",\"standing\":\"opened\"},{\"rate_id\":\"18\",\"budget_type\":\"pre-paid\",\"service_item_id\":\"384\",\"rate_type\":\"object\",\"date_closed\":null,\"date_commenced\":\"1496282400\",\"contract_id\":\"44\",\"date_expires\":\"1498788000\",\"standing\":\"opened\",\"contract_budget\":\"284\",\"contract\":\"contracts/44\",\"duration_type\":\"fixed\",\"id\":\"285\",\"rollover\":\"no\",\"date_created\":\"1505195967\",\"allowance_type\":\"fixed_value\"},{\"rollover\":\"no\",\"id\":\"286\",\"date_created\":\"1505195968\",\"allowance_type\":\"fixed_value\",\"contract\":\"contracts/44\",\"duration_type\":\"fixed\",\"date_closed\":null,\"date_commenced\":\"1498874400\",\"date_expires\":\"1501466400\",\"contract_id\":\"44\",\"contract_budget\":\"285\",\"standing\":\"opened\",\"rate_id\":\"18\",\"service_item_id\":\"385\",\"rate_type\":\"object\",\"budget_type\":\"pre-paid\"},{\"date_created\":\"1505195968\",\"rollover\":\"no\",\"id\":\"287\",\"allowance_type\":\"fixed_value\",\"contract\":\"contracts/44\",\"duration_type\":\"fixed\",\"date_closed\":null,\"date_commenced\":\"1501552800\",\"contract_budget\":\"286\",\"standing\":\"opened\",\"date_expires\":\"1504144800\",\"contract_id\":\"44\",\"rate_id\":\"18\",\"rate_type\":\"object\",\"service_item_id\":\"386\",\"budget_type\":\"pre-paid\"},{\"date_created\":\"1505195969\",\"rollover\":\"no\",\"id\":\"288\",\"allowance_type\":\"fixed_value\",\"contract\":\"contracts/44\",\"duration_type\":\"fixed\",\"date_closed\":null,\"date_commenced\":\"1504231200\",\"contract_budget\":\"287\",\"standing\":\"opened\",\"date_expires\":\"1506736800\",\"contract_id\":\"44\",\"rate_id\":\"18\",\"rate_type\":\"object\",\"service_item_id\":\"387\",\"budget_type\":\"pre-paid\"},{\"standing\":\"opened\",\"contract_budget\":\"300\",\"contract_id\":\"44\",\"date_expires\":\"1509411600\",\"date_closed\":null,\"date_commenced\":\"1506819600\",\"budget_type\":\"pre-paid\",\"service_item_id\":\"400\",\"rate_type\":\"object\",\"rate_id\":\"18\",\"allowance_type\":\"fixed_value\",\"date_created\":\"1506758893\",\"rollover\":\"no\",\"id\":\"301\",\"duration_type\":\"fixed\",\"contract\":\"contracts/44\"}]},\"meta\":{\"status\":\"ok\",\"message\":\"Everything executed as expected.\",\"more_info\":\"https://api.accelo.com/docs/#status-codes\"}}";
		
		
		HTTPResponse httpResponse = new HTTPResponse(200,"b",sourceJson);
		
		try
		{
			ResponseContactPeriods response = httpResponse.parseBody(ResponseContactPeriods.class);
			
			for ( ContractPeriod period : response.getList())
			{
				System.out.println(period);
			}
		}
		catch (AcceloException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public class ResponseContactPeriods  
	{
		Response response;

		public List<ContractPeriod> getList()
		{
			return response.getList();
		}
		

	}
	
	class Response
	{
		List<ContractPeriod> periods;

		public List<ContractPeriod> getList()
		{
			return periods;
		}

	}


}
