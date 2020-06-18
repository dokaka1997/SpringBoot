package com.viettel.arpu.model.request.mb;

import com.viettel.arpu.validator.Phone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @Author VuHQ
 * @Since 6/4/2020
 */
@Getter
@Setter
@NoArgsConstructor
public class MbGetLimitInMBForm {
    @Phone
    @NotBlank
    private String sourceMobile;
    @NotBlank
    private String sourceNumber;
    @NotBlank
    private String identityCardType;
    @NotBlank
    private String identityCardNumber;
    @NotBlank
    @Range(min = 1, max = 2)
    private String loanType;
}
