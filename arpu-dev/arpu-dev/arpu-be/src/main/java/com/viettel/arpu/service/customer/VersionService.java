package com.viettel.arpu.service.customer;

import com.viettel.arpu.model.entity.Version;

/**
 * @author trungnb3
 * @Date :6/3/2020, Wed
 */
public interface VersionService {
    Version createVersionByBatch(String batchName);

    Long getLatestVersionForBatchId(Long batchId);
}
