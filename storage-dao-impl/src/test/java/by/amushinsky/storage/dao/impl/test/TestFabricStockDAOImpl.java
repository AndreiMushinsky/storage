package by.amushinsky.storage.dao.impl.test;

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

import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.dao.config.DataConfig;
import by.amushinsky.storage.core.FabricStock;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFabricStockDAOImpl 
{
	@Autowired
	private FabricStockDAO fabricStockDAO;
	
	private static List<FabricStock> expectedStocks; 
	
	@BeforeClass
	public static void init()
	{
		expectedStocks = new ArrayList<>();
		expectedStocks.add(new FabricStock("linen 100%", new BigDecimal("500.00")));
		expectedStocks.add(new FabricStock("cotton 100%", new BigDecimal("460.10")));
		expectedStocks.add(new FabricStock("cot/lin 50/50", new BigDecimal("1200.50")));
		expectedStocks.add(new FabricStock("PE 100%", new BigDecimal("799.50")));
	}
	
	@Test
	public void testContext()
	{
		Assert.assertNotNull(fabricStockDAO);
	}
	
	@Test
	public void testGetTotalAmount()
	{
		Assert.assertEquals(new BigDecimal("2960.10"), fabricStockDAO.getTotalAmount());
	}
	
	@Test
	public void testGetStocks()
	{
		List<FabricStock> actualStocks = fabricStockDAO.getStocks();
		Assert.assertEquals(4, actualStocks.size());
		for(int i = 0; i < expectedStocks.size(); i++)
		{
			Assert.assertEquals(expectedStocks.get(i), actualStocks.get(i));
		}
	}
	
}
