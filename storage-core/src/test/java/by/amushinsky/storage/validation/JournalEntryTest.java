package by.amushinsky.storage.validation;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import by.amushinsky.storage.core.JournalEntry;

public class JournalEntryTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDateIsNull() {
        JournalEntry entry = new JournalEntry(1, new Date(), 1, true,
                BigDecimal.ONE);

        Set<ConstraintViolation<JournalEntry>> constraintViolations = validator
                .validate(entry);
        assertEquals(0, constraintViolations.size());

        entry.setDate(null);
        constraintViolations = validator.validate(entry);

        ConstraintViolation<JournalEntry> violation = constraintViolations
                .iterator().next();
        assertEquals(null, violation.getInvalidValue());
        assertEquals("date", violation.getPropertyPath().toString());
    }

    @Test
    public void testAmountIsNull() {
        JournalEntry entry = new JournalEntry(1, new Date(), 1, true,
                BigDecimal.ONE);

        Set<ConstraintViolation<JournalEntry>> constraintViolations = validator
                .validate(entry);
        assertEquals(0, constraintViolations.size());

        entry.setAmount(null);
        constraintViolations = validator.validate(entry);

        ConstraintViolation<JournalEntry> violation = constraintViolations
                .iterator().next();
        assertEquals(null, violation.getInvalidValue());
        assertEquals("amount", violation.getPropertyPath().toString());
    }

    @Test
    public void testAmountMinSize() {
        JournalEntry entry = new JournalEntry(1, new Date(), 1, true,
                BigDecimal.ZERO);

        Set<ConstraintViolation<JournalEntry>> constraintViolations = validator
                .validate(entry);
        assertEquals(1, constraintViolations.size());

        ConstraintViolation<JournalEntry> violation = constraintViolations
                .iterator().next();
        assertEquals(BigDecimal.ZERO, violation.getInvalidValue());
        assertEquals("amount", violation.getPropertyPath().toString());
    }

    @Test
    public void testAmountMinDigits() {
        BigDecimal tooLongIntegerPart = new BigDecimal("10e13");
        JournalEntry entry = new JournalEntry(1, new Date(), 1, true,
                tooLongIntegerPart);

        Set<ConstraintViolation<JournalEntry>> constraintViolations = validator
                .validate(entry);
        assertEquals(1, constraintViolations.size());

        ConstraintViolation<JournalEntry> violation = constraintViolations
                .iterator().next();
        assertEquals(tooLongIntegerPart, violation.getInvalidValue());
        assertEquals("amount", violation.getPropertyPath().toString());

        BigDecimal tooLongFractionPart = new BigDecimal("1.001");
        entry.setAmount(tooLongFractionPart);
        constraintViolations = validator.validate(entry);
        assertEquals(1, constraintViolations.size());

        violation = constraintViolations.iterator().next();
        assertEquals(tooLongFractionPart, violation.getInvalidValue());
        assertEquals("amount", violation.getPropertyPath().toString());
    }
}
