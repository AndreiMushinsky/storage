package by.amushinsky.storage.mvc.controller.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.web.servlet.view.InternalResourceView;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.core.TrialBalance;
import by.amushinsky.storage.mvc.controller.TrialBalanceController;
import by.amushinsky.storage.service.api.TrialBalanceService;

public class TrialBalanceControllerTest {
	@Test
	public void testGetTrialBalance() throws Exception {
		TimePeriod timePeriod = new TimePeriod(new GregorianCalendar(2015, Calendar.APRIL, 1), 
				new GregorianCalendar(2015, Calendar.APRIL, 1));
		TrialBalance trialBalance = createTrialBalance(timePeriod);
		TrialBalanceService service = Mockito.mock(TrialBalanceService.class);
		Mockito.when(service.getTrialBalance(timePeriod)).thenReturn(trialBalance);
		Mockito.when(service.getTrialBalance(Mockito.any(TimePeriod.class))).thenReturn(trialBalance);

		TrialBalanceController controller = new TrialBalanceController();
		controller.setTrialBalanceService(service);

		MockMvc mockMvc = standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB_INF/views/trial.jsp")).build();
		mockMvc.perform(get("/trial")).andExpect(view().name("trial"))
				.andExpect(model().attributeExists("trialBalance", "timePeriod"))
				.andExpect(model().attribute("trialBalance", CoreMatchers.equalTo(trialBalance)));
		mockMvc.perform(post("/trial").requestAttr("timePeriod", timePeriod))
				.andExpect(view().name("trial"))
				.andExpect(model().attributeExists("trialBalance", "timePeriod"))
				.andExpect(model().attribute("timePeriod", CoreMatchers.equalTo(timePeriod)))
				.andExpect(model().attribute("trialBalance", CoreMatchers.equalTo(trialBalance)));
	}

	public TrialBalance createTrialBalance(TimePeriod timePeriod) {
		TotalMovement expectedTotalMovement = new TotalMovement(new BigDecimal("1560.20"), new BigDecimal("4000.60"),
				new BigDecimal("1100.20"), new BigDecimal("4460.60"));
		List<FabricMovement> expectedMovements = new ArrayList<>();
		expectedMovements.add(new FabricMovement("cotton 100%", new BigDecimal("560.20"), new BigDecimal("0.00"),
				new BigDecimal("100.10"), new BigDecimal("460.10")));
		expectedMovements.add(new FabricMovement("cot/lin 50/50", new BigDecimal("0.00"), new BigDecimal("1700.60"),
				new BigDecimal("500.10"), new BigDecimal("1200.50")));
		expectedMovements.add(new FabricMovement("linen 100%", new BigDecimal("1000.00"), new BigDecimal("0.00"),
				new BigDecimal("500.00"), new BigDecimal("500.00")));
		expectedMovements.add(new FabricMovement("PE 100%", new BigDecimal("0.00"), new BigDecimal("2300.00"),
				new BigDecimal("0.00"), new BigDecimal("2300.00")));
		return new TrialBalance(expectedMovements, expectedTotalMovement, timePeriod);
	}
}
