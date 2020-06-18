package com.viettel.arpu.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NumericValidator implements ConstraintValidator<Numeric, String> {
    private static Pattern patternNumeric = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Override
    public void initialize(Numeric constraintAnnotation) {

    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(number)) {
            return false;
        }
        return patternNumeric.matcher(number).matches();
    }
}
