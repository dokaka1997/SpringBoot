package com.viettel.arpu.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viettel.arpu.constant.CommonConstant;
import com.viettel.arpu.locale.Translator;
import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.utils.ObjectMapperUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity(name = "Loan")
@Table(name = "tbl_loan")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Loan extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    private Interest interest;
    @ManyToOne
    @JsonIgnore
    private Customer customer;

    private BigDecimal arpuLatestThreeMonths;

    private BigDecimal loanAmount;

    private String repaymentForm;

    private BigDecimal minimumLimit;

    private BigDecimal maximumLimit;

    @ManyToOne
    private CodeCode approvalStatus;

    @ManyToOne
    private CodeCode loanStatus;

    @OneToOne
    private CustomerRef customerRef;

    private BigDecimal fee;

    private String contractLink;

    private String loanAccount;
    private BigDecimal amountSpent;
    private BigDecimal limitRemaining;
    private BigDecimal profitAmount;
    private BigDecimal amountPay;
    private LocalDate expirationDate;
    private String reasonRejection;
    private byte isAutomaticPayment;

    public LoanDTO toLoanDTO() {
        LoanDTO loanDTO = ObjectMapperUtils.map(this, LoanDTO.class);
        Customer customer = getCustomer();
        BeanUtils.copyProperties(customer,loanDTO, "id","createdDate");

        loanDTO.setGender(Translator.toLocale(customer.getGender().toString()));
        loanDTO.setId(String.valueOf(getId()));

        loanDTO.setMaximumLimit(Optional.ofNullable(getMaximumLimit())
                .map(bigDecimal -> String.valueOf(bigDecimal.intValue()))
                .orElse(CommonConstant.DEFAULT_LIMIT));
        loanDTO.setMinimumLimit(Optional.ofNullable(getMaximumLimit())
                .map(bigDecimal -> String.valueOf(bigDecimal.intValue()))
                .orElse(CommonConstant.DEFAULT_LIMIT));
        loanDTO.setLoanAmount(Optional.ofNullable(getLoanAmount())
                .map(bigDecimal -> String.valueOf(bigDecimal.intValue()))
                .orElse(CommonConstant.DEFAULT_LIMIT));

        loanDTO.setLoanStatus(Optional.ofNullable(getLoanStatus())
                                            .flatMap(codeCode -> Optional.of(codeCode.getId()))
                                            .orElse(""));
        loanDTO.setApproveStatus(Optional.ofNullable(getApprovalStatus())
                                            .flatMap(codeCode -> Optional.of(codeCode.getId()))
                                            .orElse(""));

        Optional.ofNullable(getInterest()).ifPresent(interest1 -> {
            loanDTO.setInterestRate(interest1.getInterestRate());
            loanDTO.setLoanTerm(interest1.getLoanTerm());
        });

        return loanDTO;
    }
}

