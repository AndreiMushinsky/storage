package by.amushinsky.storage.dao.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import by.amushinsky.storage.utils.config.UtilsConfig;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Import(UtilsConfig.class)
@ComponentScan(basePackages = { "by.amushinsky.storage.dao.*" })
public class DataConfig {
	@Configuration
	@Profile("default")
	@PropertySource("classpath:/props/data.properties")
	static class DatabaseConfig {
		@Autowired
		Environment env;

		@Bean
		public DataSource dataSource() {
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
	@PropertySource("classpath:/props/test/test-data.properties")
	static class EmbeddedDatabaseConfig {
		@Autowired
		Environment env;

		@Bean
		public DataSource embeddedDataSource() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
					.addScript(env.getProperty("test.schema")).addScript(env.getProperty("test.data")).build();
		}
	}

	@Bean
	public DataSourceTransactionManager txManager(DataSource dataSource) {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource);
		return txManager;
	}
}
