package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

import static com.viettel.arpu.constant.ErrorConstant.CODE_INPUT_INVALID;

/**
 * @author DoDV
 * @Date :6/11/2020, Thu
 */
public class CodeInvalidException extends RuntimeException implements ErrorResponseSupport {
    public CodeInvalidException() {
        super(CODE_INPUT_INVALID.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return CODE_INPUT_INVALID;
    }
}
