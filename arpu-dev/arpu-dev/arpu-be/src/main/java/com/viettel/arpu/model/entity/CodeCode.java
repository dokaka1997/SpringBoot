package com.viettel.arpu.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CodeCode")
@Table(name = "tbl_code_code")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CodeCode {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    public CodeCode() {

    }

    public CodeCode(String id) {
        this.id = id;
    }

    private String codeName;

    private String codeType;

    private String codeTypeName;
}
