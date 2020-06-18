package com.viettel.arpu.model.dto;

import com.viettel.arpu.model.entity.Reference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
public class CustomerRefDTO {
    private String relationship;
    @ManyToOne
    private Reference reference;
}
