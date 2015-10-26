package by.amushinsky.storage.validation;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import by.amushinsky.storage.core.TimePeriod;

public class TimePeriodTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDateIsNull() {
        TimePeriod timePeriod = new TimePeriod(null, null);
        Set<ConstraintViolation<TimePeriod>> constraintViolations = validator
                .validate(timePeriod);

        assertEquals(2, constraintViolations.size());
        ConstraintViolation<TimePeriod> violation = constraintViolations
                .iterator().next();
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void testValidTimePeriod() {
        Calendar day = new GregorianCalendar();
        Date now = day.getTime();
        day.add(Calendar.DAY_OF_YEAR, 1);
        Date tommorow = day.getTime();

        TimePeriod timePeriod = new TimePeriod(now, tommorow);
        Set<ConstraintViolation<TimePeriod>> constraintViolations = validator
                .validate(timePeriod);
        assertEquals(0, constraintViolations.size());

        timePeriod = new TimePeriod(tommorow, now);
        constraintViolations = validator.validate(timePeriod);
        assertEquals(1, constraintViolations.size());
    }

}
