package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.CUSTOMER_HAS_BEEN_UNLOCK;

/**
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
public class CustomerHasBeenUnLockException extends RuntimeException implements ErrorResponseSupport {
    public CustomerHasBeenUnLockException() {
        super(CUSTOMER_HAS_BEEN_UNLOCK.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return CUSTOMER_HAS_BEEN_UNLOCK;
    }
}
