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

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.dao.api.TrialBalanceDAO;
import by.amushinsky.storage.dao.config.DataConfig;

@ActiveProfiles("dev")
@ContextConfiguration(classes = DataConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTrialBalanceDAOImpl {
	@Autowired
	private TrialBalanceDAO trialBalanceDAO;

	@Test
	public void testContext() {
		Assert.assertNotNull(trialBalanceDAO);
	}

	@Test
	public void testGetTotalMovement() {
		TotalMovement actualTotalMovement = trialBalanceDAO.getTotalMovement(ETALON_TIMEPERIOD);
		Assert.assertEquals(ETALON_TOTAL_MOVEMENT, actualTotalMovement);
	}

	@Test
	public void testEmptyPeriod() {
		TotalMovement actualTotalMovement = trialBalanceDAO.getTotalMovement(ETALON_EMPTY_TIMEPERIOD);
		Assert.assertEquals(ETALON_EMPTY_TOTAL_MOVEMENT, actualTotalMovement);
	}

	@Test
	public void testGetMovements() {
		List<FabricMovement> actualMovements = trialBalanceDAO.getMovements(ETALON_TIMEPERIOD);
		Assert.assertTrue(ETALON_MOVEMENTS.containsAll(actualMovements) && actualMovements.containsAll(ETALON_MOVEMENTS));
	}
}
