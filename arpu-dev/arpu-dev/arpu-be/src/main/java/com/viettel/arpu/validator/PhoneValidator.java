package com.viettel.arpu.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author VuHQ
 * @Since 6/2/2020
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(phoneNumber)) {
            return true;
        }
        return phoneNumber.matches("^[0-9]*$") && phoneNumber.length() <= 12;
    }
}
