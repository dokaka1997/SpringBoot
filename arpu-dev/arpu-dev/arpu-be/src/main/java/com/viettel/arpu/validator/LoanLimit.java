package com.viettel.arpu.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoanLimitValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface LoanLimit {
    String message() default "{constraints.LoanAmount}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String maxLimit();

    String minLimit();

    String amount();

}
