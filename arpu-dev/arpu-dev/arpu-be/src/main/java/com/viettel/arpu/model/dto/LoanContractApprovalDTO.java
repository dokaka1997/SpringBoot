package com.viettel.arpu.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoanContractApprovalDTO {
    Object customerInfo;
    Object loanInfo;
    Object referenceInfo;

}
