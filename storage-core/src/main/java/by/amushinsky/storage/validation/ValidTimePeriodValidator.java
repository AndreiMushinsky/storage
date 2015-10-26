package by.amushinsky.storage.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.amushinsky.storage.core.TimePeriod;

public class ValidTimePeriodValidator
        implements ConstraintValidator<ValidTimePeriod, TimePeriod> {

    @Override
    public void initialize(ValidTimePeriod annotation) {}

    @Override
    public boolean isValid(TimePeriod timePeriod,
            ConstraintValidatorContext validatorContext) {
        if (timePeriod == null)
            return true;
        if (timePeriod.getFromDate() == null || timePeriod.getToDate() == null)
            return true;
        return timePeriod.getToDate().compareTo(timePeriod.getFromDate()) >= 0;
    }

}
