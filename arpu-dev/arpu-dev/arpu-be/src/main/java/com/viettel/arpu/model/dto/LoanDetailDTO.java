package com.viettel.arpu.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LoanDetailDTO {

    LoanInfoDTO loanInfo;
    CustomerDTO customerInfo;
    ReferenceDTO referenceInfo;
    private String contractLink;
}
