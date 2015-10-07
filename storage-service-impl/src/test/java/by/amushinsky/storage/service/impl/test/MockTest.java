package by.amushinsky.storage.service.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.core.TrialBalance;
import by.amushinsky.storage.dao.api.FabricDAO;
import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.dao.api.JournalEntryDAO;
import by.amushinsky.storage.dao.api.TrialBalanceDAO;
import by.amushinsky.storage.dao.impl.FabricStockDAOImpl;
import by.amushinsky.storage.service.impl.JournalServiceImpl;
import by.amushinsky.storage.service.impl.StorageServiceImpl;
import by.amushinsky.storage.service.impl.TrialBalanceServiceImpl;

public class MockTest {

	public static final int ETALON_ID = 1;
	public static final BigDecimal ETALON_AMOUNT = new BigDecimal("100.00");
	public static final List<FabricStock> ETALON_STOCKS = new ArrayList<>();
	public static final Storage ETALON_STORAGE = new Storage(ETALON_STOCKS, ETALON_AMOUNT);
	public static final Date ETALON_FROM_DATE = new GregorianCalendar(2015, Calendar.SEPTEMBER, 7).getTime();
	public static final Date ETALON_TO_DATE = new GregorianCalendar(2015, Calendar.SEPTEMBER, 14).getTime();
	public static final TimePeriod ETALON_TIMEPERIOD = new TimePeriod(ETALON_FROM_DATE, ETALON_TO_DATE);
	public static final TotalMovement ETALON_TOTAL_MOVEMENT = new TotalMovement(new BigDecimal("1560.20"),
			new BigDecimal("4000.60"), new BigDecimal("1100.20"), new BigDecimal("4460.60"));
	public static final List<FabricMovement> ETALON_MOVEMENTS = new ArrayList<>();
	public static final TrialBalance ETALON_TRIALBALANCE = new TrialBalance(ETALON_MOVEMENTS, ETALON_TOTAL_MOVEMENT,
			ETALON_TIMEPERIOD);
	public static final List<Fabric> ETALON_FABRICS = new ArrayList<>();

	private StorageServiceImpl storageService;
	private TrialBalanceServiceImpl trialBalanceService;
	private JournalServiceImpl journalService;

	@Before
	public void init() {
		FabricStockDAO fabricStockDAO = Mockito.mock(FabricStockDAOImpl.class);
		Mockito.when(fabricStockDAO.getStocks()).thenReturn(ETALON_STOCKS);
		Mockito.when(fabricStockDAO.getTotalAmount()).thenReturn(ETALON_AMOUNT);
		Mockito.when(fabricStockDAO.getAmountById(ETALON_ID)).thenReturn(ETALON_AMOUNT);
		
		JournalEntryDAO journalEntryDAO = Mockito.mock(JournalEntryDAO.class);
		
		FabricDAO fabricDAO = Mockito.mock(FabricDAO.class);
		Mockito.when(fabricDAO.getFabrics()).thenReturn(ETALON_FABRICS);
		
		storageService = new StorageServiceImpl();
		storageService.setFabricStockDAO(fabricStockDAO);
		storageService.setFabricDAO(fabricDAO);

		TrialBalanceDAO trialBalanceDAO = Mockito.mock(TrialBalanceDAO.class);
		Mockito.when(trialBalanceDAO.getMovements(ETALON_TIMEPERIOD)).thenReturn(ETALON_MOVEMENTS);
		Mockito.when(trialBalanceDAO.getTotalMovement(ETALON_TIMEPERIOD)).thenReturn(ETALON_TOTAL_MOVEMENT);
		
		trialBalanceService = new TrialBalanceServiceImpl();
		trialBalanceService.setTrialBalanceDAO(trialBalanceDAO);
		
		journalService = new JournalServiceImpl();
		journalService.setFabricDAO(fabricDAO);
		journalService.setJournalEntryDAO(journalEntryDAO);
	}

	@Test
	public void testGetStorage() {
		Assert.assertEquals(ETALON_STORAGE, storageService.getStorage());
	}

	@Test
	public void testGetAmountById() {
		Assert.assertEquals(ETALON_AMOUNT, storageService.getAmountById(ETALON_ID));
	}
	
	@Test
	public void testGetFabrics() {
		Assert.assertEquals(ETALON_FABRICS, storageService.getFabrics());
	}

	@Test
	public void testGetTrialBalance() {
		Assert.assertEquals(ETALON_TRIALBALANCE, trialBalanceService.getTrialBalance(ETALON_TIMEPERIOD));
	}
	
	@Test
	public void testCreateEntryWithFabric(){
		FabricDAO fabricDAO = Mockito.mock(FabricDAO.class);
		JournalEntryDAO journalEntryDAO = Mockito.mock(JournalEntryDAO.class);
		JournalServiceImpl testJournalService = new JournalServiceImpl();
		testJournalService.setFabricDAO(fabricDAO);
		testJournalService.setJournalEntryDAO(journalEntryDAO);
		testJournalService.createEntryWithFabric(null, null);
		Mockito.verify(fabricDAO, Mockito.times(1)).create(null);
		Mockito.verify(journalEntryDAO, Mockito.times(1)).create(null);
	}
}
