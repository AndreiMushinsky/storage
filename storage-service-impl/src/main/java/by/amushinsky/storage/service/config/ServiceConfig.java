package by.amushinsky.storage.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import by.amushinsky.storage.dao.config.DataConfig;
import by.amushinsky.storage.utils.config.UtilsConfig;

@Configuration
@Import({ DataConfig.class, UtilsConfig.class })
@ComponentScan(basePackages = { "by.amushinsky.storage.service.*" })
public class ServiceConfig {
}
