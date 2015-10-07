package by.amushinsky.storage.mvc.controller.test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.web.context.WebApplicationContext;

import by.amushinsky.storage.core.FabricMovement;
import by.amushinsky.storage.core.TimePeriod;
import by.amushinsky.storage.core.TotalMovement;
import by.amushinsky.storage.core.TrialBalance;
import by.amushinsky.storage.mvc.config.WebConfig;
import by.amushinsky.storage.service.api.TimePeriodService;
import by.amushinsky.storage.service.api.TrialBalanceService;
import by.amushinsky.storage.service.config.ServiceConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, ServiceConfig.class})
@Profile("dev")
@WebAppConfiguration
public class TrialBalanceControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private TrialBalanceService trialBalanceService;
	
	@Autowired
	private TimePeriodService timePeriodService;
	
    private MockMvc mockMvc;
    private TrialBalance defaultTrialBalance;
    private TimePeriod defaultTimePeriod;
    private TimePeriod timePeriod;
    private TrialBalance trialBalance;
    
    @Before
    public void setUp() throws Exception {
    	defaultTimePeriod = timePeriodService.defaultTimePeriod();
		defaultTrialBalance = trialBalanceService.getTrialBalance(defaultTimePeriod);
		timePeriod = new TimePeriod(new GregorianCalendar(2015, Calendar.SEPTEMBER, 7).getTime(), 
				new GregorianCalendar(2015, Calendar.SEPTEMBER, 14).getTime());
		trialBalance = createTrialBalance(timePeriod);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
	
	
	@Test
	public void testGetTrialBalance() throws Exception {
		
		mockMvc.perform(get("/trial")).andExpect(view().name("trial"))
				.andExpect(model().attributeExists("trialBalance", "timePeriod"))
				.andExpect(model().attribute("timePeriod", defaultTimePeriod))
				.andExpect(model().attribute("trialBalance", defaultTrialBalance));
		mockMvc.perform(post("/trial").param("fromDate", "07.09.2015").param("toDate", "14.09.2015"))
				.andExpect(view().name("trial"))
				.andExpect(model().attributeExists("trialBalance", "timePeriod"))
				.andExpect(model().attribute("timePeriod", timePeriod));
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
		expectedMovements.sort( (x, y) -> x.getName().compareTo(y.getName()) );
		return new TrialBalance(expectedMovements, expectedTotalMovement, timePeriod);
	}
}
