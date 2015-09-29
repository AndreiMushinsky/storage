package by.amushinsky.storage.dao.logging;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DAOLogger {
	private Logger logger;

	@Autowired
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Around("execution(* by.amushinsky.storage.dao.api.*.get*(..))")
	public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		logger.trace("start " + joinPoint);
		try {
			result = joinPoint.proceed();
		} catch (Throwable exception) {
			logger.error("exception while " + joinPoint + "\n" + exception);
			throw exception;
		}
		logger.trace("end " + joinPoint);
		return result;
	}

}
