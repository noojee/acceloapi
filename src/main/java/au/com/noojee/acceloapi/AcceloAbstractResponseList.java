package au.com.noojee.acceloapi;

import java.util.List;

import au.com.noojee.acceloapi.dao.AcceloResponseMeta;
import au.com.noojee.acceloapi.entities.AcceloEntity;

public abstract class AcceloAbstractResponseList<E extends AcceloEntity<E>> extends  AcceloResponseMeta<E>
{
	
	abstract public List<E> getList();
}
