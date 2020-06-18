package com.viettel.arpu.constant.enums;

/**
 * @Author VuHQ
 * @Since 6/8/2020
 */
public enum ServiceChanel {
    COUNTER("0400"),
    MOBILE("0200");

    private String code;
    ServiceChanel(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
