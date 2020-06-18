package com.viettel.arpu.model.request.mb;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author VuHQ
 * @Since 6/9/2020
 */
@Getter
@Setter
public class MbFinalLoanForm {
    private String sourceMobile;
    private String sourceNumber;
    private String loanAccount;
    private String otp;
    private String finalRequestId;
}
