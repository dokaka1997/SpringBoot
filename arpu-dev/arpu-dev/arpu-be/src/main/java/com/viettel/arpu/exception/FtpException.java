package com.viettel.arpu.exception;

import com.viettel.arpu.model.response.ErrorResponse;
import com.viettel.arpu.model.response.ErrorResponseSupport;

public class FtpException  extends RuntimeException implements ErrorResponseSupport {
    public FtpException(Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorResponse toErrorResponse() {
        return null;
    }
}
