package by.amushinsky.storage.rest.servlet3;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import by.amushinsky.storage.dao.config.DataConfig;
import by.amushinsky.storage.rest.config.WebConfig;
import by.amushinsky.storage.service.config.ServiceConfig;

public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{ServiceConfig.class, DataConfig.class};
	};

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
