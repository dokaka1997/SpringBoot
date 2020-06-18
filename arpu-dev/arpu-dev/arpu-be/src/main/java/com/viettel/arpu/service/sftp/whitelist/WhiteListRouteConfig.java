package com.viettel.arpu.service.sftp.whitelist;

import com.viettel.arpu.config.FtpStorageProperties;
import com.viettel.arpu.constant.BatchConstant;
import com.viettel.arpu.model.entity.Version;
import com.viettel.arpu.service.customer.VersionService;
import com.viettel.arpu.service.audit.AuditLogService;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public class WhiteListRouteConfig extends RouteBuilder {
    private static final int LINE_COUNT = 200;

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

    public WhiteListRouteConfig(Processor customProcessor, AuditLogService auditLogService, VersionService versionService, FtpStorageProperties ftpStorageProperties, EndOfWhitelistProcessor endOfWhitelistProcessor) {
        this.customProcessor = customProcessor;
        this.auditLogService = auditLogService;
        this.versionService = versionService;
        this.ftpStorageProperties = ftpStorageProperties;
        this.endOfWhitelistProcessor = endOfWhitelistProcessor;
    }

    @Override
    public void configure() throws Exception {
        //-------------------------CSV whitelist batch-------------------------//
        try (CsvDataFormat csvParser = new CsvDataFormat(CSVFormat.DEFAULT)) {
            csvParser.setSkipHeaderRecord(true);
            csvParser.setQuoteMode(QuoteMode.ALL);
            Version version = versionService.createVersionByBatch(BatchConstant.WHITE_LIST);
            StringBuilder stringUri = new StringBuilder("sftp://");
            stringUri.append(ftpStorageProperties.getBatchUser())
                    .append("@")
                    .append(ftpStorageProperties.getHost())
                    .append(":")
                    .append(ftpStorageProperties.getPort())
                    .append(ftpStorageProperties.getBatchDir())
                    .append("?password=")
                    .append(ftpStorageProperties.getBtachPwd())
                    .append("&username=")
                    .append(ftpStorageProperties.getBatchUser())
                    .append("&fileName=")
                    .append(ftpStorageProperties.getWhitelistFile())
                    .append("&charset=UTF-8&noop=true");
            StringBuilder stringTo = new StringBuilder("controlbus:route?routeId=").append(BatchConstant.WHITE_LIST).append("&action=stop&async=true");
            from(Optional.ofNullable(stringUri.toString()).orElse(""))
                    .id(BatchConstant.WHITE_LIST)
                    .setHeader("Version", version::getVersionId)
                    .onCompletion()
                    //Gọi tới một bean sau khi hoàn thành
                    .bean(endOfWhitelistProcessor, "process")
                    .end()
                    .unmarshal(csvParser)
                    .split(body())
                    .streaming()
                    .parallelProcessing()
                    .aggregate(constant(true), new WhiteListAggregationStrategy(auditLogService))
                    .completionSize(LINE_COUNT)
                    .completionTimeout(1000)
                    .process(customProcessor)
                    //Đóng route khi hoàn thành
                    .to(stringTo.toString());
        } catch (Exception ex) {
            log.error("Create CSV file error with : {}", ex.getStackTrace());
        }
    }
}

