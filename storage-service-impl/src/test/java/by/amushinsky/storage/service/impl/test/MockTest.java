package by.amushinsky.storage.service.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.dao.impl.FabricStockDAOImpl;
import by.amushinsky.storage.service.impl.StorageServiceImpl;


public class MockTest 
{
	
	@Test
	public void testGetStorage()
	{
		FabricStockDAO fabricStockDAO = Mockito.mock(FabricStockDAOImpl.class);
		Mockito.when(fabricStockDAO.getStocks()).thenReturn(new ArrayList<>());
		Mockito.when(fabricStockDAO.getTotalAmount()).thenReturn(new BigDecimal("0.00"));
		StorageServiceImpl storageService = new StorageServiceImpl();
		storageService.setFabricStockDAO(fabricStockDAO);
		Assert.assertEquals(new Storage(new ArrayList<>(), new BigDecimal("0.00")), storageService.getStorage());
	}
	
}
