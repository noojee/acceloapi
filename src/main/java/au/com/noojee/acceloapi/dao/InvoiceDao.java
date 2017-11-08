package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.AcceloResponse;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Invoice;

public class InvoiceDao extends AcceloDao<Invoice>
{
	
	@Override
	protected EndPoint getEndPoint()
	{
		return EndPoint.invoices;
	}
	
	@Override
	protected Class<Invoice> getEntityClass()
	{
		return Invoice.class;
	}


	
	public class ResponseList extends AcceloResponseList<Invoice>
	{
	}
	
	public class Response extends AcceloResponse<Invoice>
	{
	}

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}
	
	@Override
	protected Class<? extends AcceloResponse<Invoice>> getResponseClass()
	{
		return Response.class;
	}


	

}
