package com.viettel.arpu.validator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * @Author VuHQ
 * @Since 6/5/2020
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
   private String toDate;
   private String fromDate;
   public void initialize(FieldMatch constraint) {
      toDate = constraint.toDate();
      fromDate = constraint.fromDate();
   }

   public boolean isValid(final Object value, final ConstraintValidatorContext context) {
      try {
         final String firstValue = BeanUtils.getProperty(value,fromDate);
         final String secondValue = BeanUtils.getProperty(value, toDate);

         LocalDate firstDate = LocalDate.parse(firstValue);
         LocalDate secondDate = LocalDate.parse(secondValue);
         if (secondDate!=null && firstDate!=null && secondDate.isBefore(firstDate)){
            return false;
         }
         return true;
      } catch (Exception e) {
         return true;
      }
   }
}
