package au.com.noojee.acceloapi.dao;

import au.com.noojee.acceloapi.AcceloApi;
import au.com.noojee.acceloapi.AcceloException;
import au.com.noojee.acceloapi.AcceloResponseList;
import au.com.noojee.acceloapi.EndPoint;
import au.com.noojee.acceloapi.entities.Invoice;

public class InvoiceDao extends AcceloDao<Invoice, InvoiceDao.ResponseList>
{

	@Override
	protected Class<ResponseList> getResponseListClass()
	{
		return ResponseList.class;
	}

	@Override
	public Invoice getById(AcceloApi acceloApi, int id) throws AcceloException
	{

		return super.getById(acceloApi, EndPoint.invoices, id);
	}

	public class ResponseList extends AcceloResponseList<Invoice>
	{
	}

}
