package com.viettel.arpu.model.response.mb;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author VuHQ
 * @Since 6/12/2020
 */
@Getter
@Setter
public class MbLoanInfoResponse {
    private String loanAccount;
    private String customerAccount;
    private String term;
    private String startDate;
    private String endDate;
    private String interestRate;

}
