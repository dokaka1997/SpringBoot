package com.viettel.arpu.model.dto.mb;

import com.viettel.arpu.model.response.mb.MbBaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
@Getter
@Setter
@NoArgsConstructor
public class MbCheckLoanDTO extends MbBaseResponse {
    private String sourceMobile;
    private String sourceNumber;
    private String identityCardType;
    private String identityCardNumber;
    private String loanType;
}
