package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.REASON;

/**
 * @author DoDV
 * @Date :6/16/2020, Tue
 */
public class ReasonMissingException extends RuntimeException implements ErrorResponseSupport {
    public ReasonMissingException() {
        super(REASON.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return REASON;
    }
}
