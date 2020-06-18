package com.viettel.arpu.model.request.mb;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author VuHQ
 * @Since 6/11/2020
 */
@Getter
@Setter
public class MbSendToMbApprovalForm extends MbLoanRegistrationForm {
    private Integer scoreMin;
    private Integer scoreMax;
    private BigDecimal scoreAve;
}
