package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.LOAN_STATUS_INVALID;

/**
 * @author DoDV
 * @Date :6/11/2020, Thu
 */
public class LoanStatusInvalidException extends RuntimeException implements ErrorResponseSupport {
    public LoanStatusInvalidException() {
        super(LOAN_STATUS_INVALID.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return LOAN_STATUS_INVALID;
    }
}
