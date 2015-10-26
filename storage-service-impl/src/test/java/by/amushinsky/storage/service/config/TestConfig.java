package by.amushinsky.storage.service.config;

import javax.validation.Validator;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import by.amushinsky.storage.dao.api.FabricsDAO;
import by.amushinsky.storage.dao.api.JournalDAO;

@Configuration
@Import(ServiceConfig.class)
public class TestConfig {

	@Bean
	public JournalDAO mockJournalDAO(){
		return Mockito.mock(JournalDAO.class);
	}
	
	@Bean
	public FabricsDAO mockFabricsDAO(){
		return Mockito.mock(FabricsDAO.class);
	}
	
	@Bean
	@Primary
	public Validator mockValidator(){
		return Mockito.mock(Validator.class);
	}
}
