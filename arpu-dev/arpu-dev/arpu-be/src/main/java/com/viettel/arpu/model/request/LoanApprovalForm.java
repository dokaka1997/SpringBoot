package com.viettel.arpu.model.request;

import com.viettel.arpu.model.response.mb.MbBaseResponse;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoanApprovalForm extends MbBaseResponse {
    @NotNull
    private Long loanId;
    @NotBlank
    private String codeId;
    private String reason;
    @NotBlank
    private String sourceMobile;
    @NotBlank
    private String sourceNumber;
    @NotBlank
    private String identityCardType;
    @NotBlank
    private String identityCardNumber;
}
