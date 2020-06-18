package com.viettel.arpu.service.sftp.whitelist;

import com.viettel.arpu.constant.BatchConstant;
import com.viettel.arpu.locale.Translator;
import com.viettel.arpu.model.entity.*;
import com.viettel.arpu.service.sftp.form.CustomerForm;
import com.viettel.arpu.service.audit.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;


@Slf4j
@Service
public class WhiteListAggregationStrategy implements AggregationStrategy {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private int countRecord = 0;
    private AuditLogService auditLogService;

    public WhiteListAggregationStrategy() {
    }

    @Autowired
    protected WhiteListAggregationStrategy(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Validator validator = factory.getValidator();
        List<String> newBody = newExchange.getIn().getBody(ArrayList.class);
        VersionId versionId = (VersionId) newExchange.getMessage().getHeader("Version");
        countRecord++;
        CustomerForm customerForm = null;
        Customer customer = new Customer();
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = true;
        if (newBody.size() > 13) {
            try {
                customerForm = CustomerForm.builder()
                        .msisdn(newBody.get(1).trim())
                        .fullName(newBody.get(2).trim())
                        .dateOfBirth(newBody.get(3).trim())
                        .gender(newBody.get(4).trim())
                        .identityType(newBody.get(5).trim())
                        .identityNumber(newBody.get(6).trim())
                        .dateOfIssue(newBody.get(7).trim())
                        .placeOfIssue(newBody.get(8).trim())
                        .viettelpayWallet(newBody.get(9).trim())
                        .scoreMin(newBody.get(14).trim())
                        .scoreMax(newBody.get(15).trim())
                        .build();
                Set<ConstraintViolation<CustomerForm>> constraintViolations = validator.validate(customerForm);
                if (!constraintViolations.isEmpty()) {
                    flag = false;
                    for (ConstraintViolation<CustomerForm> violation : constraintViolations) {
                        stringBuilder.append(Translator.toLocale(violation.getMessage()));
                    }
                } else {
                    customer = CustomerForm.from(customerForm);
                }
            } catch (Exception ex) {
                log.error("Builder Customer error with : {}", ex.getStackTrace());
            }
        } else {
            flag = false;
            stringBuilder.append(BatchConstant.ERROR_SYNC_LENGTH_FILE);
        }
        if (!flag) {
            log.info("=============Ghi dữ liệu lỗi vào AuditLog==============");
            try {
                SyncAuditLog syncAuditLog = SyncAuditLog.builder()
                        .batchId(versionId.getBatchId())
                        .recordNumber(Long.valueOf(countRecord))
                        .recordContent(newBody.toString())
                        .reason(stringBuilder.toString())
                        .build();
                auditLogService.save(syncAuditLog);
            } catch (Exception ex) {
                log.error("Audiology for sync whitelist error with:", ex.getStackTrace());
            }
        }
        List<Object> list = new ArrayList<>();
        if (Objects.equals(null, oldExchange)) {
            list.add(customer);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(customer);
            return oldExchange;
        }
    }
}
