package com.viettel.arpu.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class ReferenceDTO {
    private String fullName;
    private String relationship;
    private String msisdn;
    private String email;
}
