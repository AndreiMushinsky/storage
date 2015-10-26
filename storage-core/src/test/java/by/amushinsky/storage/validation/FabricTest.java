package by.amushinsky.storage.validation;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import by.amushinsky.storage.core.Fabric;

public class FabricTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testNameIsNull() {
        Fabric fabric = new Fabric(null);
        Set<ConstraintViolation<Fabric>> constraintViolations = validator
                .validate(fabric);

        assertEquals(1, constraintViolations.size());
        ConstraintViolation<Fabric> violation = constraintViolations.iterator()
                .next();
        assertEquals(null, violation.getInvalidValue());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    public void testNameSize() {
        String tooShort = Stream.generate(() -> '_')
                .limit(Fabric.NAME_MIN_LENGTH - 1).toString();
        String tooLong = Stream.generate(() -> '_')
                .limit(Fabric.NAME_MAX_LENGTH + 1).toString();

        Fabric fabric = new Fabric();

        fabric.setName(tooShort);
        Set<ConstraintViolation<Fabric>> constraintViolations = validator
                .validate(fabric);
        assertEquals(1, constraintViolations.size());
        ConstraintViolation<Fabric> violation = constraintViolations.iterator()
                .next();
        assertEquals(tooShort, violation.getInvalidValue());
        assertEquals("name", violation.getPropertyPath().toString());

        fabric.setName(tooLong);
        constraintViolations = validator.validate(fabric);
        assertEquals(1, constraintViolations.size());
        violation = constraintViolations.iterator().next();
        assertEquals(tooLong, violation.getInvalidValue());
        assertEquals("name", violation.getPropertyPath().toString());
    }

}
