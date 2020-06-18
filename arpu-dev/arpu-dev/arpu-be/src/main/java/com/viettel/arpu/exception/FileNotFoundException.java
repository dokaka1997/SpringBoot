package com.viettel.arpu.exception;

import com.viettel.arpu.constant.AppConstants;
import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

/**
 * @author DoDV
 * @Date :6/5/2020, Fri
 */
public class FileNotFoundException extends RuntimeException implements ErrorResponseSupport {
    private static final ErrorResponse error = new ErrorResponse(AppConstants.FILE_NOT_FOUND);

    public FileNotFoundException() {
        super(error.getMessage());
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return error;
    }
}
