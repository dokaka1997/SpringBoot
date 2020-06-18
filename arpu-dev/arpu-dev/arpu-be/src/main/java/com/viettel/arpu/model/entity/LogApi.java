package com.viettel.arpu.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author trungnb3
 * @Date :5/29/2020, Fri
 */
@Entity(name = "LogApi")
@Table(name = "tbl_log_api")
@Getter
@Setter
public class LogApi extends AbstractAuditingEntity {
    @Id
    @Column(name = "requestId")
    private String requestId;
    @Column(name = "request_header_info",length = 65535,columnDefinition="Text")
    private String requestHeaderInfo;
    @Column(name = "request_body_info",length = 65535,columnDefinition="Text")
    private String requestBodyInfo;
    @Column(name = "response_body_info",length = 65535,columnDefinition="Text")
    private String responseBodyInfo;
}
