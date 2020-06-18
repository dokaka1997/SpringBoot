package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.CUSTOMER_HAS_BEEN_LOCK;

/**
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
public class CustomerHasBeenLockException extends RuntimeException implements ErrorResponseSupport {
    public CustomerHasBeenLockException() {
        super(CUSTOMER_HAS_BEEN_LOCK.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return CUSTOMER_HAS_BEEN_LOCK;
    }
}
