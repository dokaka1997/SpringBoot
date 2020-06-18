package com.viettel.arpu.model.entity;

import com.viettel.arpu.model.dto.ReferenceDTO;
import com.viettel.arpu.utils.ObjectMapperUtils;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Reference")
@Table(name = "tbl_reference")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String fullName;
    private String msisdn;
    private String email;

    @OneToMany(mappedBy = "reference")
    private Set<CustomerRef> customerRef = new HashSet<>();

    public Reference() {
    }
}

