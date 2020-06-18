package com.viettel.arpu.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanInfoDTO {
    private Long id;
    private BigDecimal arpuLatestThreeMonths;
    private BigDecimal loanAmount;
    private String loanTerm;
    private String repaymentForm;
    private BigDecimal minimumLimit;
    private BigDecimal maximumLimit;
    private BigDecimal amountSpent;
    private BigDecimal limitRemaining;
    private BigDecimal profitAmount;
    private BigDecimal amountPay;
    private LocalDate expirationDate;
    private String approvalStatus;
    private String loanStatus;
    private String reasonRejection;
    private String loanAccount;
    private String requestId;
    private String interestRate;
}
