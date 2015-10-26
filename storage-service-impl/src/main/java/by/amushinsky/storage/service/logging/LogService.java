package by.amushinsky.storage.service.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import by.amushinsky.storage.utils.Loggable;

@Aspect
@Component
public class LogService {

	private static final Logger logger = LogManager.getLogger(LogService.class.getName());
	
	@Pointcut("execution(@by.amushinsky.storage.utils.Loggable * *(..)) && @annotation(loggable)")
	public void logging(Loggable loggable) {}
	
	@Around("logging(loggable)")
	public Object log(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		String clazz = joinPoint.getSignature().getDeclaringTypeName();
		String method = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		boolean returnVoid = joinPoint.getSignature().toShortString().contains("void");

		Object result = null;
		long executionTime = 0;

		try {
			long startTime = System.nanoTime();
			result = joinPoint.proceed();
			executionTime = System.nanoTime() - startTime;
		} catch (Exception exception) {
			logger.error("{} #{}{}: {}", clazz, method, args, exception);
			logger.catching(Level.ERROR, exception);
			throw exception;
		}
		if (returnVoid)
			logger.info("{} #{}{} in {} ms", clazz, method, args, executionTime / 1000);
		else if(result == null)
			logger.info("{} #{}{} => {} in {} ms", clazz, method, args, result, executionTime / 1000);
		else {
			String resultClass = result.getClass().getSimpleName();
			logger.info("{} #{}{} => {}: {} in {} μsec", clazz, method, args, resultClass, result, executionTime / 1000);
		}
		return result;
	}

}
