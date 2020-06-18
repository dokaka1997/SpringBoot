package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.LOAN_HAS_BEEN_APPROVED;

/**
 * @author DoDV
 * @Date :6/10/2020, Wed
 */
public class LoanHasBeenApprovedException extends RuntimeException implements ErrorResponseSupport {
    public LoanHasBeenApprovedException() {
        super(LOAN_HAS_BEEN_APPROVED.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return LOAN_HAS_BEEN_APPROVED;
    }
}
