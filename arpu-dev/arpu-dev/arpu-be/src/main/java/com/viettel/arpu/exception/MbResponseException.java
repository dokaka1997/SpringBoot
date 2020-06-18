package com.viettel.arpu.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author VuHQ
 * @Since 6/2/2020
 */
@Getter
@Setter
@ToString
public class MbResponseException extends RuntimeException {
    private String code;
    private String message;
    public MbResponseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
