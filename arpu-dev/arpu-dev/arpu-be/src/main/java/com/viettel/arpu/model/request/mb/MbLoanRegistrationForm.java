package com.viettel.arpu.model.request.mb;

import com.viettel.arpu.validator.LoanLimit;
import com.viettel.arpu.validator.Phone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
@Getter
@Setter
@NoArgsConstructor
//@LoanLimit(maxLimit = "loanMinimumLimit", minLimit = "loanMaximumLimit", amount = "amount")
public class MbLoanRegistrationForm {
    @NotBlank
    @Phone
    private String sourceMobile;
    @NotBlank
    private String sourceNumber;
    @NotBlank
    private String email;
    @NotNull
    @Past
    private LocalDate birthday;
    @NotBlank
    @Range(min = 1, max = 2)
    private String gender;
    @NotBlank
    private String nationality;
    @NotBlank
    private String identityCardType;
    @NotBlank
    private String identityCardNumber;
    @NotNull
    @Past
    private LocalDate issueDate;
    @NotBlank
    private String issuePlace;
    @NotBlank
    private String term;
    @NotBlank
    private String payType;
    @NotBlank
    @Range(min = 1, max = 2)
    private String loanType;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private BigDecimal fee;
    private String referencerName;
    private String referencerType;
    private String referencerMobile;
    private String referencerEmail;
    private String village;
    private String district;
    private String province;
    private String addressDetail;
    @NotNull
    private BigDecimal loanMinimumLimit;
    @NotNull
    private BigDecimal loanMaximumLimit;
    @NotNull
    private byte isAutomaticPayment;
}
