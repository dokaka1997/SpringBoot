package com.viettel.arpu.validator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author VuHQ
 * @Since 6/16/2020
 */
public class LoanLimitValidator implements ConstraintValidator<LoanLimit, Object> {
   private String minLimit;
   private String maxLimit;
   private String amount;
   public void initialize(LoanLimit constraint) {
      minLimit = constraint.minLimit();
      maxLimit = constraint.maxLimit();
      amount = constraint.amount();
   }

   public boolean isValid(final Object value, final ConstraintValidatorContext context) {
      try {
         final BigDecimal minLimitValue = new BigDecimal(BeanUtils.getProperty(value,minLimit));
         final BigDecimal maxLimitValue = new BigDecimal(BeanUtils.getProperty(value, maxLimit));
         final BigDecimal amountValue = new BigDecimal(BeanUtils.getProperty(value, amount));

         if ( minLimitValue.compareTo(amountValue) >= 0 && amountValue.compareTo(maxLimitValue) <= 0) {
            return true;
         }
         return false;
      } catch (Exception e) {
         return true;
      }
   }
}
