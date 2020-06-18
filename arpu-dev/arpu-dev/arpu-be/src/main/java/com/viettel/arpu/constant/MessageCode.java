package com.viettel.arpu.constant;

public class MessageCode {
    protected String code;
    protected String message;

    public MessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MessageCode(String code) {
        this(code, "");
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
