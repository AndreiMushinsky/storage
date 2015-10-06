package by.amushinsky.storage.dao.impl.test;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static by.amushinsky.storage.dao.impl.test.Etalons.*;

import by.amushinsky.storage.dao.api.FabricStockDAO;
import by.amushinsky.storage.dao.config.DataConfig;
import by.amushinsky.storage.core.FabricStock;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestFabricStockDAOImpl {
	@Autowired
	private FabricStockDAO fabricStockDAO;

	@Test
	public void testContext() {
		Assert.assertNotNull(fabricStockDAO);
	}

	@Test
	public void testGetTotalAmount() {
		Assert.assertEquals(ETALON_TOTAL_AMOUNT, fabricStockDAO.getTotalAmount());
	}
	
	@Test
	public void testGetAmountById() {
		Assert.assertEquals(ETALON_FABRIC_AMOUNT, fabricStockDAO.getAmountById(ETALON_FABRIC_ID));
	}
	
	@Test
	public void testGetStocks() {
		List<FabricStock> actualStocks = fabricStockDAO.getStocks();
		for (int i = 0; i < ETALON_STOCKS.size(); i++) {
			Assert.assertEquals(ETALON_STOCKS.get(i), actualStocks.get(i));
		}
	}

}
