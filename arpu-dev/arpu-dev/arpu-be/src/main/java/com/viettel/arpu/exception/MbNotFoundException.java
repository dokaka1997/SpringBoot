package com.viettel.arpu.exception;

import com.viettel.arpu.locale.Translator;

/**
 * @Author VuHQ
 * @Since 6/1/2020
 */
public class MbNotFoundException extends RuntimeException {
    public MbNotFoundException(String message) {
        super(Translator.toLocale(message));
    }
}
