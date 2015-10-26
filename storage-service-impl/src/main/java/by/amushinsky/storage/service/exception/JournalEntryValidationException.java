package by.amushinsky.storage.service.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import by.amushinsky.storage.core.JournalEntry;

public class JournalEntryValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Set<ConstraintViolation<JournalEntry>> violations;

	public JournalEntryValidationException(Set<ConstraintViolation<JournalEntry>> violations) {
		this.setViolations(violations);
	}

	public Set<ConstraintViolation<JournalEntry>> getViolations() {
		return violations;
	}

	public void setViolations(Set<ConstraintViolation<JournalEntry>> violations) {
		this.violations = violations;
	}
	
	
}
