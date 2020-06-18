package com.viettel.arpu.model.request.mb;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author VuHQ
 * @Since 6/2/2020
 */
@Data
public class MbPayLoanForm {
    private BigDecimal amount;
    private String sourceMobile;
    private String sourceNumber;
    private String loanAccount;
    private String otp;
    private String payRequestId;
}
