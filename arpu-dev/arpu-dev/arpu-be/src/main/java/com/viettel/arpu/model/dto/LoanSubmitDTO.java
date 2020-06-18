package com.viettel.arpu.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LoanSubmitDTO {
    Object customerInfo;
    Object loanInfo;
    Object referenceInfo;

    public Object getCustomerInfo() {

        return customerInfo;
    }

    public void setCustomerInfo(Object customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Object getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(Object loanInfo) {
        this.loanInfo = loanInfo;
    }

    public Object getReferenceInfo() {
        return referenceInfo;
    }

    public void setReferenceInfo(Object referenceInfo) {
        this.referenceInfo = referenceInfo;
    }
}
