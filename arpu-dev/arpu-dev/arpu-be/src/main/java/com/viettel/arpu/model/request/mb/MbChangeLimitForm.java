package com.viettel.arpu.model.request.mb;

import com.viettel.arpu.validator.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @Author VuHQ
 * @Since 6/1/2020
 */
@Getter
@Setter
public class MbChangeLimitForm {
    @NotBlank
    @Phone
    private String sourceMobile;
    @NotBlank
    private String sourceNumber;
    @NotBlank
    private String loanAccount ;
    @NotBlank
    private String identityCardType;
    @NotBlank
    private String identityCardNumber;
    @NotBlank
    private BigDecimal changeAmount;
    private String otp;

}
