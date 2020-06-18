package com.viettel.arpu.service.audit;

import com.viettel.arpu.model.entity.SyncAuditLog;

public interface AuditLogService {
    void save(SyncAuditLog syncAuditLog);
}
