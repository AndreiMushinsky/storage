package by.amushinsky.storage.service.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.service.api.StorageService;
import by.amushinsky.storage.service.config.ServiceConfig;

@ActiveProfiles("dev")
@ContextConfiguration(classes=ServiceConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IntegrationTest 
{
	@Autowired
	private StorageService storageService;
	
	private static Storage expectedStorage;

	@BeforeClass
	public static void init()
	{
		List<FabricStock> expectedStocks = new ArrayList<>();
		expectedStocks.add(new FabricStock("linen 100%", new BigDecimal("500.00")));
		expectedStocks.add(new FabricStock("cotton 100%", new BigDecimal("460.10")));
		expectedStocks.add(new FabricStock("cot/lin 50/50", new BigDecimal("1200.50")));
		expectedStocks.add(new FabricStock("PE 100%", new BigDecimal("799.50")));
		expectedStorage = new Storage(expectedStocks, new BigDecimal("2960.10"));
	}
	
	@Test
	public void testContext()
	{
		Assert.assertNotNull(storageService);
	}
	
	@Test
	public void testGetStorage()
	{
		Assert.assertEquals(expectedStorage, storageService.getStorage());
	}
}
