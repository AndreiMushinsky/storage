package by.amushinsky.storage.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValidTimePeriodValidator.class })
@Documented
public @interface ValidTimePeriod {
    String message() default "{by.amushinsky.storage.validation.core.ValidTimePeriod.message}";

    Class<?>[]groups() default {};

    Class<? extends Payload>[]payload() default {};
}
