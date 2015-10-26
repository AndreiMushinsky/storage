package by.amushinsky.storage.service.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import by.amushinsky.storage.core.TimePeriod;

public class TimePeriodValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Set<ConstraintViolation<TimePeriod>> violations;

	public TimePeriodValidationException(Set<ConstraintViolation<TimePeriod>> violations) {
		this.setViolations(violations);
	}

	public Set<ConstraintViolation<TimePeriod>> getViolations() {
		return violations;
	}

	public void setViolations(Set<ConstraintViolation<TimePeriod>> violations) {
		this.violations = violations;
	}
	
	
}
