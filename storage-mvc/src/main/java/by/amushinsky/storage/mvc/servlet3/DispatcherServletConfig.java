package by.amushinsky.storage.mvc.servlet3;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import by.amushinsky.storage.mvc.config.WebConfig;
import by.amushinsky.storage.service.config.ServiceConfig;
import by.amushinsky.storage.utils.config.UtilsConfig;

public class DispatcherServletConfig 
	extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses() 
	{
		return new Class[]{ServiceConfig.class, UtilsConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() 
	{
		return new Class[]{WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() 
	{
		return new String[]{"/"};
	}

}
