package by.amushinsky.storage.core;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class FabricStockTest {
	private FabricStock fabricStock = new FabricStock("test", new BigDecimal(100.50));

	@Test
	public void testGetName() {
		Assert.assertEquals("test", fabricStock.getName());
	}

	@Test
	public void testGetAmount() {
		Assert.assertEquals(new BigDecimal(100.5), fabricStock.getAmount());
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWrong() {
		new FabricStock(null, null).getName().length();
	}

}
