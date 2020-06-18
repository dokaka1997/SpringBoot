package com.viettel.arpu.exception;

import com.viettel.arpu.constant.AppConstants;
import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

/**
 * @Author VuHQ
 * @Since 6/3/2020
 */
public class LoanNotFoundException extends RuntimeException implements ErrorResponseSupport {
    private static final ErrorResponse error = new ErrorResponse(AppConstants.LOAN_NOT_FOUND);

    public LoanNotFoundException() {
        super(error.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return error;
    }
}
