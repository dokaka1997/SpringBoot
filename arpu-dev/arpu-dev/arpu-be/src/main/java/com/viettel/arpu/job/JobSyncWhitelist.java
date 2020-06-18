package com.viettel.arpu.job;

import com.viettel.arpu.config.FtpStorageProperties;
import com.viettel.arpu.constant.BatchConstant;
import com.viettel.arpu.model.entity.JobConfig;
import com.viettel.arpu.repository.JobConfigRepository;
import com.viettel.arpu.service.audit.AuditLogService;
import com.viettel.arpu.service.customer.VersionService;
import com.viettel.arpu.service.sftp.whitelist.EndOfWhitelistProcessor;
import com.viettel.arpu.service.sftp.whitelist.WhiteListRouteConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class JobSyncWhitelist {
    private static final String CRON_DEFAULT = "0 0 0 * * *";
    @Autowired
    JobConfigRepository jobConfigRepository;

    @Autowired
    CamelContext camelContext;

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

    @Scheduled(cron = "#{@getCronValue}")
    public void syncWhiteList() throws Exception {
        camelContext.addRoutes(new WhiteListRouteConfig(customProcessor, auditLogService,
                versionService, ftpStorageProperties, endOfWhitelistProcessor));
    }

    @Bean
    public String getCronValue() {
        try {
            Optional<JobConfig> jobConfig = jobConfigRepository.findById(BatchConstant.WHITE_LIST_BATCH_ID);
            return jobConfig.map(JobConfig::getCron).orElse(CRON_DEFAULT);
        } catch (Exception ex) {
            log.error("Error when get cronValue" + ex.getStackTrace());
            return CRON_DEFAULT;
        }
    }
}
