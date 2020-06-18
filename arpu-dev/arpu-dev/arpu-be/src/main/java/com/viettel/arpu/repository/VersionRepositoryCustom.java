package com.viettel.arpu.repository;


public interface VersionRepositoryCustom {
    Long getLatestVersionForBatchId(Long batchId);
}
