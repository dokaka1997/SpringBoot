package com.viettel.arpu.service.sftp.form;

import com.viettel.arpu.constant.BatchConstant;
import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.validator.*;
import lombok.*;
import com.viettel.arpu.constant.enums.Gender;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
public class CustomerForm implements Serializable {

    @NotNull
    @Phone(message = "error.msg.sync.phone")
    private String msisdn;
    @NotNull
    private String fullName;
    @NotNull
    @Date(message = "error.msg.sync.whitelist.date.of_birth")
    private String dateOfBirth;
    @NotNull
    @Sex(message = "error.msg.sync.whitelist.gender")
    private String gender;
    @NotNull
    @IdentityType(message = "error.msg.sync.whitelist.identity_type")
    private String identityType;
    @NotNull
    @Numeric(message = "error.msg.sync.whitelist.identity_number")
    private String identityNumber;
    @NotNull
    @Date(message = "error.msg.sync.whitelist.date.of_issue")
    private String dateOfIssue;
    @NotNull
    private String placeOfIssue;
    private String loanMinimum;
    private String loanMaximum;
    @NotNull
    @Numeric(message = "error.msg.sync.whitelist.score_min")
    private String scoreMin;
    @NotNull
    @Numeric(message = "error.msg.sync.whitelist.score_max")
    private String scoreMax;
    private String nationality;
    private String batchStatus;
    private String customerAccount;
    private String viettelpayWallet;

    public static Customer from(CustomerForm opt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return Optional.ofNullable(Customer.builder()
                .fullName(opt.getFullName())
                .msisdn(opt.getMsisdn())
                .dateOfBirth(LocalDate.parse(opt.getDateOfBirth(), formatter))
                .gender((Objects.equals(BatchConstant.MALE, opt.getGender().toUpperCase()) ? Gender.MALE : Gender.FEMALE))
                .identityType(opt.getIdentityType())
                .identityNumber(opt.getIdentityNumber())
                .dateOfIssue(LocalDate.parse(opt.getDateOfIssue(), formatter))
                .placeOfIssue(opt.getPlaceOfIssue())
                .scoreMin(Integer.valueOf(opt.getScoreMin()))
                .scoreMax(Integer.valueOf(opt.getScoreMax()))
                .build()).orElse(new Customer());
    }

}