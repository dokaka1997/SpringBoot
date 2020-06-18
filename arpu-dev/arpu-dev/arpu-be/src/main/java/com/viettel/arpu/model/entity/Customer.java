package com.viettel.arpu.model.entity;

import com.viettel.arpu.constant.enums.Gender;
import com.viettel.arpu.locale.Translator;
import com.viettel.arpu.model.dto.CustomerDTO;
import com.viettel.arpu.utils.ObjectMapperUtils;
import com.viettel.arpu.utils.SyncStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity(name = "Customer")
@Table(name = "tbl_customer", uniqueConstraints = {
        @UniqueConstraint( columnNames = "msisdn"),
        @UniqueConstraint( columnNames = "identityNumber"),
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Customer extends AbstractAuditingEntity {

    public static final String INACTIVE = "inactive";
    public static final String ACTIVE = "active";

    public enum LOCK_STATUS {
        LOCK, UNLOCK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String msisdn;
    private String fullName;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String identityType;
    private String identityNumber;
    private LocalDate dateOfIssue;
    private String placeOfIssue;
    private BigDecimal loanMinimum;
    private BigDecimal loanMaximum;
    private Long arpu;
    private Integer scoreMin;
    private Integer scoreMax;
    private BigDecimal arpuLatestThreeMonths;

    @Enumerated(value = EnumType.STRING)
    private LOCK_STATUS lockStatus;

    @OneToMany(mappedBy = "customer")
    private Set<CustomerRef> customerRef = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Loan> loans = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private SyncStatus syncStatus;

    @OneToOne
    private Address address;

    private Long version;
    private String nationality;
    private String batchStatus;
    private String customerAccount;
    private String viettelpayWallet;
    private String email;

    public CustomerDTO toDTO(Long latestVersion) {
        CustomerDTO customerDTO = ObjectMapperUtils.map(this, CustomerDTO.class);

        String lockStatus = Optional.ofNullable(getLockStatus())
                .map(Enum::name)
                .orElse(LOCK_STATUS.UNLOCK.name()).toLowerCase();

        customerDTO.setLockStatusId(lockStatus);
        customerDTO.setLockStatus(Translator.toLocale(lockStatus));

        customerDTO.setActiveStatus(Translator.toLocale(
                getVersion() < latestVersion ? INACTIVE : ACTIVE
        ));
        customerDTO.setGender(Translator.toLocale(gender.name()));
        return customerDTO;
    }
}
