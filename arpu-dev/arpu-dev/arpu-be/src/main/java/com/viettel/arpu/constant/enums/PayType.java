package com.viettel.arpu.constant.enums;

import lombok.Getter;

/**
 * @Author VuHQ
 * @Since 6/15/2020
 */
@Getter
public enum PayType {
    PAY_TYPE_01("1"),
    PAY_TYPE_02("2");

    private String id;

    PayType(String id) {
        this.id = id;
    }
}
