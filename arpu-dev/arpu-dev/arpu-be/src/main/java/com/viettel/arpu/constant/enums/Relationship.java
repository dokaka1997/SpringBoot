package com.viettel.arpu.constant.enums;

import lombok.Getter;

/**
 * @Author VuHQ
 * @Since 6/15/2020
 */
@Getter
public enum Relationship {
    MQHS_01("1"),
    MQHS_02("2"),
    MQHS_03("3"),
    MQHS_04("4"),
    MQHS_05("5"),
    MQHS_06("6");

    private String id;

    Relationship(String id) {
        this.id = id;
    }
}
