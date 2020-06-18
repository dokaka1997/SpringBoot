package com.viettel.arpu.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author VuHQ
 * @Since 5/25/2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    @JsonProperty("loanId")
    private String id;
    private String fullName;
    @JsonProperty("phone")
    private String msisdn;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("identityNumber")
    private String identityNumber;
    @JsonProperty("identityType")
    private String identityType;
    @JsonProperty("dateOfIssue")
    private LocalDate dateOfIssue;
    @JsonProperty("placeOfIssue")
    private String placeOfIssue;
    @JsonProperty("minLimit")
    private String minimumLimit;
    @JsonProperty("maxLimit")
    private String maximumLimit;
    @JsonProperty("loanStatus")
    private String loanStatus;
    @JsonProperty("loanAmount")
    private String loanAmount;
    @JsonProperty("loanDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdDate;
    @JsonProperty("loanTerm")
    private String loanTerm;
    @JsonProperty("approveStatus")
    private String approveStatus;
    @JsonProperty("interestRate")
    private String interestRate;
}
