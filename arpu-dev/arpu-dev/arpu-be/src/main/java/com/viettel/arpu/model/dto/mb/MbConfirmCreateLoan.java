package com.viettel.arpu.model.dto.mb;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author DoDV
 * @Date :6/10/2020, Wed
 */
@Setter
@Getter
public class MbConfirmCreateLoan {
    @NotNull
    private String sourceMobile;
    @NotNull
    private String sourceNumber;
    @NotNull
    private String identityCardType;
    @NotNull
    private String identityCardNumber;
    @NotNull
    private String kyc;
}
