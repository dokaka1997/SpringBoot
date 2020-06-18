package com.viettel.arpu.model.response.mb;

import lombok.*;

/**
 * @Author VuHQ
 * @Since 6/2/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MbBaseResponse {
    private String errorCode;
    private String errorDesc;
}
