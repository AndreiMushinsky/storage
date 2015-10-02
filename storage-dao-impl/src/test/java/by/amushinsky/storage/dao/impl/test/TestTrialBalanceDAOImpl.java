package by.amushinsky.storage.dao.impl.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.dao.api.TrialBalanceDAO;
import by.amushinsky.storage.dao.config.DataConfig;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTrialBalanceDAOImpl {
	@Autowired
	private TrialBalanceDAO trialBalanceDAO;

	private static TotalMovement expectedTotalMovement;
	private static List<FabricMovement> expectedMovements;
	private static TimePeriod timePeriod;

	@BeforeClass
	public static void init() {
		expectedTotalMovement = new TotalMovement(new BigDecimal("1560.20"), new BigDecimal("4000.60"),
				new BigDecimal("1100.20"), new BigDecimal("4460.60"));
		expectedMovements = new ArrayList<>();
		expectedMovements.add(new FabricMovement("cotton 100%", new BigDecimal("560.20"), new BigDecimal("0.00"),
				new BigDecimal("100.10"), new BigDecimal("460.10")));
		expectedMovements.add(new FabricMovement("cot/lin 50/50", new BigDecimal("0.00"), new BigDecimal("1700.60"),
				new BigDecimal("500.10"), new BigDecimal("1200.50")));
		expectedMovements.add(new FabricMovement("linen 100%", new BigDecimal("1000.00"), new BigDecimal("0.00"),
				new BigDecimal("500.00"), new BigDecimal("500.00")));
		expectedMovements.add(new FabricMovement("PE 100%", new BigDecimal("0.00"), new BigDecimal("2300.00"),
				new BigDecimal("0.00"), new BigDecimal("2300.00")));
		timePeriod = new TimePeriod(new GregorianCalendar(2015, Calendar.SEPTEMBER, 7),
				new GregorianCalendar(2015, Calendar.SEPTEMBER, 14));
	}

	@Test
	public void testContext() {
		Assert.assertNotNull(trialBalanceDAO);
	}

	@Test
	public void testGetTotalMovement() {
		TotalMovement actualTotalAmount = trialBalanceDAO.getTotalMovement(timePeriod);
		Assert.assertEquals(expectedTotalMovement, actualTotalAmount);
	}

	@Test
	public void testEmptyPeriod() {
		TotalMovement actualTotalAmount = trialBalanceDAO
				.getTotalMovement(new TimePeriod(new GregorianCalendar(1991, Calendar.SEPTEMBER, 7),
						new GregorianCalendar(1991, Calendar.SEPTEMBER, 14)));

		Assert.assertEquals(new TotalMovement(new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"),
				new BigDecimal("0.00")), actualTotalAmount);
	}

	@Test
	public void testGetMovements() {
		List<FabricMovement> actualMovements = trialBalanceDAO.getMovements(timePeriod);
		Assert.assertEquals(expectedMovements.size(), actualMovements.size());
		for (int i = 0; i < actualMovements.size(); i++)
			Assert.assertTrue(expectedMovements.contains(actualMovements.get(i)));
	}
}
