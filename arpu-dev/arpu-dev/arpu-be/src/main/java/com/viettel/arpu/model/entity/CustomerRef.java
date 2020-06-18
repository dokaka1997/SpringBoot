package com.viettel.arpu.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "CustomerRef")
@Table(name = "tbl_customer_ref")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class CustomerRef extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    private CodeCode relationship;
    @ManyToOne
    @JsonIgnore
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL)
    private Reference reference;
}
