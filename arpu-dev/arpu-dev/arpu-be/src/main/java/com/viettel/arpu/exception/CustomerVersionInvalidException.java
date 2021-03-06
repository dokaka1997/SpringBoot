package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.CUSTOMER_VERSION_INVALID;

/**
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
public class CustomerVersionInvalidException extends RuntimeException implements ErrorResponseSupport {
    public CustomerVersionInvalidException() {
        super(CUSTOMER_VERSION_INVALID.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return CUSTOMER_VERSION_INVALID;
    }
}
