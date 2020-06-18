package com.viettel.arpu.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CodeCodeDTO {
    private Long id;

    private String codeName;

    private String codeType;

    private String codeTypeName;
}
