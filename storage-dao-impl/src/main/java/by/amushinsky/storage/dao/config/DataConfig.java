package by.amushinsky.storage.dao.config;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"by.amushinsky.storage.dao.*"})
public class DataConfig 
{	
	@Configuration
	@Profile("default")
	@PropertySource("classpath:/by/amushinsky/storage/dao/config/data.properties")
	static class DatabaseConfig
	{
		@Autowired
		Environment env;
		
		@Bean
		public DataSource dataSource()
		{
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(env.getProperty("database.driver"));
			dataSource.setUrl(env.getProperty("database.url"));
			dataSource.setUsername(env.getProperty("database.user"));
			dataSource.setPassword(env.getProperty("database.password"));
			return dataSource;
		}
		
	}
	@Configuration
	@Profile("dev")
	@PropertySource("classpath:/by/amushinsky/storage/dao/config/test/data.properties")
	static class EmbeddedDatabaseConfig
	{
		@Bean
		public DataSource embeddedDataSource() 
		{
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
					.addScript("by/amushinsky/storage/dao/sql/test/schema.sql")
					.addScript("by/amushinsky/storage/dao/sql/test/data.sql")
					.build();
		}		
	}	
	
	@Bean
	public DataSourceTransactionManager txManager(DataSource dataSource)
	{
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource);
		return txManager;
	}
	
	@Bean
	public Logger logger()
	{
		return LogManager.getLogger();
	}
}
