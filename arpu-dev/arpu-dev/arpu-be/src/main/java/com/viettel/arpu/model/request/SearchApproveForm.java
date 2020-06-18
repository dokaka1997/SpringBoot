package com.viettel.arpu.model.request;

import com.viettel.arpu.validator.FieldMatch;
import com.viettel.arpu.validator.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * @Author VuHQ
 * @Since 5/21/2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(fromDate = "fromDate", toDate = "toDate")
public class SearchApproveForm {
    private LocalDate fromDate;
    private LocalDate toDate;
    @Phone
    private String phoneNumber;
    @NotBlank
    private String approveStatus;
    private String identityNumber;
}
