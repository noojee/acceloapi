package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Invoice;

public class InvoiceDao extends AcceloDao<Invoice>
{

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}
	
	
	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.invoices;
	}

	public class ResponseList extends AcceloResponseList<Invoice>
	{
	}

}
