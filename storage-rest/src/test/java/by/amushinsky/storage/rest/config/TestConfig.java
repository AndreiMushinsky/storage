package by.amushinsky.storage.rest.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import by.amushinsky.storage.rest.controller.FabricControllerTest;
import by.amushinsky.storage.service.api.FabricService;
import by.amushinsky.storage.service.api.JournalService;

@Configuration
@Import(WebConfig.class)
@ComponentScan(basePackageClasses=FabricControllerTest.class)
public class TestConfig {

	@Bean
	public JournalService mockJournalService(){
		JournalService journalService = Mockito.mock(JournalService.class);
		return journalService;
	}
	
	@Bean
	public FabricService mockFabricService(){
		FabricService fabricService = Mockito.mock(FabricService.class);
		return fabricService;
	}
}
