package by.amushinsky.storage.dao.logging;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FabricStockDAOLogger 
{
	private Logger logger;
	
	@Autowired
	public void setLogger(Logger logger)
	{
		this.logger = logger;
	}
	
	@Around("execution(* by.amushinsky.storage.dao.api.*.get*(..))")
	public Object doLogging(ProceedingJoinPoint pjp) throws Throwable
	{
		Object result = null;
		logger.trace("start "+pjp);
		try
		{
			result = pjp.proceed();
		}
		catch(Throwable e)
		{
			logger.error("exception while "+pjp+"\n"+e.getStackTrace());
			throw e;
		}
		logger.trace("end "+pjp);
		return result;
	}
	
}
