package com.viettel.arpu.service.customer.impl;

import com.viettel.arpu.constant.BatchConstant;
import com.viettel.arpu.model.entity.Version;
import com.viettel.arpu.model.entity.VersionId;
import com.viettel.arpu.repository.VersionRepository;
import com.viettel.arpu.service.customer.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Service
@Slf4j
public class VersionServiceImpl implements VersionService {
    private VersionRepository versionRepository;
    private long batchId;
    private long initVersionValue;
    private String batchName;

    @Autowired
    public VersionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public Version createVersionByBatch(@NotNull String batchType) {
        switch (batchType) {
            case BatchConstant.WHITE_LIST:
                batchId = BatchConstant.WHITE_LIST_BATCH_ID;
                initVersionValue = BatchConstant.WHITE_LIST_INIT_VERSION_VALUE;
                batchName = BatchConstant.WHITE_LIST_BATCH_NAME;
                break;
            //todo : case cho nợ, lãi suất
            default:
                break;
        }
        Long version = versionRepository.getLatestVersionForBatchId(batchId);
        if (Objects.equals(null, version)) {
            log.info("Version for {} is not existed yet", batchName);
            version = initVersionValue;
        } else {
            log.info("Version for {} is existed", batchName);
            version++;
        }
        Version entity = new Version();
        VersionId id = new VersionId(version, batchId);
        entity.setVersionId(id);
        entity.setBatchName(batchName);
        versionRepository.save(entity);
        log.info("Latest version for {} is : {} ", batchName, version);
        return entity;
    }

    @Override
    public Long getLatestVersionForBatchId(Long batchId) {
        return versionRepository.getLatestVersionForBatchId(batchId);
    }

}
