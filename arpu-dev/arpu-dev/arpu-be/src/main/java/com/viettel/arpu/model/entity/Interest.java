package com.viettel.arpu.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Interest")
@Table(name = "tbl_interest")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Interest extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String status;
    private String loanTerm;
    private String interestRate;
    private String description;
    private String name;

}
