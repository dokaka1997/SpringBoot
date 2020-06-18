package com.viettel.arpu.constant.enums;

/**
 * @Author VuHQ
 * @Since 6/8/2020
 */
public enum  ApprovalStatus {
    WAIT_FINALIZING_CASE("PDS_01")
    , WAIT_MB_APPROVAL("PDS_02")
    , APPROVED("PDS_03")
    , REFUGE_APPROVAL("PDS_04")
    , STATUS_ALL("all");

    private String status;

    ApprovalStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}

