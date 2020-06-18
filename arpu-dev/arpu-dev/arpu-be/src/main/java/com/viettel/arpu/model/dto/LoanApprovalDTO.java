package com.viettel.arpu.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LoanApprovalDTO {
    Object customerInfo;
    Object loanInfo;
    Object referenceInfo;
    private String linkContract;
}
