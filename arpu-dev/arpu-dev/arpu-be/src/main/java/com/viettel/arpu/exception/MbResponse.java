package com.viettel.arpu.exception;

import com.viettel.arpu.constant.AppConstants;
import com.viettel.arpu.model.response.ErrorResponse;

/**
 * @Author VuHQ
 * @Since 6/2/2020
 */
public class MbResponse extends ErrorResponse {

    private MbResponse(String code, String message) {
        super(code, message, null);
    }

    public static MbResponse from(String code, String message) {
        return new MbResponse(code, message);
    }

    public void check() {
        if (!getCode().equalsIgnoreCase(AppConstants.OK.getCode())) {
            throw new MbResponseException(getCode(), this.message);
        }
    }
}
