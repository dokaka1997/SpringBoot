package com.viettel.arpu.model.request.mb;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author VuHQ
 * @Since 6/5/2020
 */
@Getter
@Setter
public class MbReduceLimitForm extends MbChangeLimitForm {
    private BigDecimal reduceAmount;
    private String reduceRequestId;
}
