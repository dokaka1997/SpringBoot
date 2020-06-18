package com.viettel.arpu.model.request.mb;

import com.viettel.arpu.validator.Phone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
@Getter
@Setter
@NoArgsConstructor
public class MbCheckCustomerForm {
    @Phone
    @NotBlank
    private String phoneNumber;
}
