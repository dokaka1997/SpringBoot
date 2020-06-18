package com.viettel.arpu.controller.sftp;

import com.viettel.arpu.config.FtpStorageProperties;
import com.viettel.arpu.model.response.BaseResponse;
import com.viettel.arpu.service.audit.AuditLogService;
import com.viettel.arpu.service.customer.VersionService;
import com.viettel.arpu.service.sftp.whitelist.EndOfWhitelistProcessor;
import com.viettel.arpu.service.sftp.whitelist.WhiteListRouteConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
@Slf4j
public class SyncController {

    @Autowired
    private CamelContext camelContext;

    @Autowired
    @Qualifier("customerProcessor")
    private Processor customProcessor;
    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    FtpStorageProperties ftpStorageProperties;

    @Autowired
    private VersionService versionService;

    @Autowired
    private EndOfWhitelistProcessor endOfWhitelistProcessor;

    @SneakyThrows
    @GetMapping("/sync/whitelist")
    @ResponseBody
    protected ResponseEntity<BaseResponse<?>> syncWhiteList() {
        camelContext.addRoutes(new WhiteListRouteConfig(customProcessor, auditLogService,
                versionService, ftpStorageProperties, endOfWhitelistProcessor));
        return ResponseEntity.ok(new BaseResponse<>());
    }
}
