package com.viettel.arpu.model.request.mb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanCreationRequestForm {
    private String sourceMobile;
    private String sourceNumber;
    private String loanRequestId;
}
