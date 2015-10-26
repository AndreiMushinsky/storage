package by.amushinsky.storage.service.fixtures;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import by.amushinsky.storage.core.JournalEntry;
import by.amushinsky.storage.core.TimePeriod;

public class JournalServiceFixtures {

	public static final int ENTRY_ID = 1;
	public static final int WRONG_ID = -1;
	public static final Date ENTRY_DATE = new Date();
	public static final int FABRIC_ID = 1;
	public static final boolean IS_DR = true;
	public static final BigDecimal ENTRY_AMOUNT = BigDecimal.ONE;

	public static JournalEntry entry() {
		return new JournalEntry(ENTRY_ID, ENTRY_DATE, FABRIC_ID, IS_DR, ENTRY_AMOUNT);
	}

	public static JournalEntry invalidEntry() {
		return new JournalEntry(ENTRY_ID, null, FABRIC_ID, IS_DR, null);
	}

	public static TimePeriod period() {
		return new TimePeriod(ENTRY_DATE, ENTRY_DATE);
	}

	public static TimePeriod invalidPeriod() {
		return new TimePeriod(null, null);
	}

	public static final Set<ConstraintViolation<JournalEntry>> entryViolations() {
		Set<ConstraintViolation<JournalEntry>> violations = new HashSet<>();
		violations.add(null);
		return violations;
	}

	public static final Set<ConstraintViolation<TimePeriod>> timePeriodViolations() {
		Set<ConstraintViolation<TimePeriod>> violations = new HashSet<>();
		violations.add(null);
		return violations;
	}
}
