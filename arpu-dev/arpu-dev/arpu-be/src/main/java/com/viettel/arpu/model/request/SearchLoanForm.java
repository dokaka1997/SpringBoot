package com.viettel.arpu.model.request;

import com.viettel.arpu.validator.FieldMatch;
import com.viettel.arpu.validator.Phone;
import lombok.*;

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
@Builder
@FieldMatch(fromDate = "fromDate", toDate = "toDate")
public class SearchLoanForm {
    private LocalDate fromDate;
    private LocalDate toDate;
    @Phone
    private String phoneNumber;
    private String identityNumber;
    @NotBlank
    private String approveStatus;
    @NotBlank
    private String loanStatus;
}
