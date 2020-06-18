package com.viettel.arpu.model.dto.mb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viettel.arpu.model.dto.CustomerDTO;
import com.viettel.arpu.model.dto.ReferenceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author VuHQ
 * @Since 6/11/2020
 */
@Getter
@Setter
public class MbCustomerDTO{
    @JsonProperty("personalInfo")
    CustomerDTO customerDTO;

    @JsonProperty("relationship")
    List<ReferenceDTO> listReference;
}
