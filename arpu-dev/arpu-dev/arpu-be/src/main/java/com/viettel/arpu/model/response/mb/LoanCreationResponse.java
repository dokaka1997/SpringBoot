package com.viettel.arpu.model.response.mb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanCreationResponse extends MbBaseResponse {
    private String errorCode;
    private String errorDesc;
    private String loanAccount;
    private String customerAccount;
    private String term;
    private String startDate;
    private String endDate;
    private String interestRate;

}
