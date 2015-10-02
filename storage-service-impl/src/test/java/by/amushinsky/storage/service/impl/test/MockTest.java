package by.amushinsky.storage.service.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.core.TrialBalance;
import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.dao.api.TrialBalanceDAO;
import by.amushinsky.storage.dao.impl.FabricStockDAOImpl;
import by.amushinsky.storage.service.impl.StorageServiceImpl;
import by.amushinsky.storage.service.impl.TrialBalanceServiceImpl;

public class MockTest {

	@Test
	public void testGetStorage() {
		FabricStockDAO fabricStockDAO = Mockito.mock(FabricStockDAOImpl.class);
		Mockito.when(fabricStockDAO.getStocks()).thenReturn(new ArrayList<>());
		Mockito.when(fabricStockDAO.getTotalAmount()).thenReturn(new BigDecimal("0.00"));
		StorageServiceImpl storageService = new StorageServiceImpl();
		storageService.setFabricStockDAO(fabricStockDAO);
		Assert.assertEquals(new Storage(new ArrayList<>(), new BigDecimal("0.00")), storageService.getStorage());
	}
	
	@Test
	public void testGetTrialBalance() {
		TrialBalanceDAO trialBalanceDAO = Mockito.mock(TrialBalanceDAO.class);
		TimePeriod timePeriod = new TimePeriod(new GregorianCalendar(2015, Calendar.SEPTEMBER, 7), 
				new GregorianCalendar(2015, Calendar.SEPTEMBER, 14));
		TotalMovement total = new TotalMovement(new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), 
				new BigDecimal("0.00"));
		
		Mockito.when(trialBalanceDAO.getMovements(timePeriod)).thenReturn(new ArrayList<>());
		Mockito.when(trialBalanceDAO.getTotalMovement(timePeriod)).thenReturn(total);

		TrialBalanceServiceImpl trialBalanceService = new TrialBalanceServiceImpl();
		trialBalanceService.setTrialBalanceDAO(trialBalanceDAO);
		Assert.assertEquals(new TrialBalance(new ArrayList<>(), total, timePeriod), trialBalanceService.getTrialBalance(timePeriod));
	}

}
