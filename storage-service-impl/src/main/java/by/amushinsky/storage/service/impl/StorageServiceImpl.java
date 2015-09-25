package by.amushinsky.storage.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.service.api.StorageService;

@Service
public class StorageServiceImpl implements StorageService
{
	
	private FabricStockDAO fabricStockDAO;
	
	@Autowired
	public void setFabricStockDAO(FabricStockDAO fabricStockDAO) 
	{
		this.fabricStockDAO = fabricStockDAO;
	}

	@Override
	public Storage getStorage() 
	{
		return new Storage(fabricStockDAO.getStocks(), fabricStockDAO.getTotalAmount());
	}

}
