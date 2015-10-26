package by.amushinsky.storage.service.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import by.amushinsky.storage.core.Fabric;

public class FabricValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Set<ConstraintViolation<Fabric>> violations;

	public FabricValidationException(Set<ConstraintViolation<Fabric>> violations) {
		this.setViolations(violations);
	}

	public Set<ConstraintViolation<Fabric>> getViolations() {
		return violations;
	}

	public void setViolations(Set<ConstraintViolation<Fabric>> violations) {
		this.violations = violations;
	}
	
	
}
