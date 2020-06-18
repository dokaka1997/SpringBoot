package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.ID_INVALID;

/**
 * @author DoDV
 * @Date :6/15/2020, Mon
 */
public class IdFormatException extends RuntimeException implements ErrorResponseSupport {

    public IdFormatException() {
        super(ID_INVALID.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return ID_INVALID;
    }
}
