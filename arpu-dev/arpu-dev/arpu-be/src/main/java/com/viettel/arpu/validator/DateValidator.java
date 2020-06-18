package com.viettel.arpu.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<Date, String> {
    public static Pattern patternDate = Pattern.compile("([0-9]{2}/([0-9]{2}/([0-9]{4})))");

    @Override
    public void initialize(Date constraintAnnotation) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(date)) {
            return false;
        }
        return patternDate.matcher(date).matches();
    }
}
