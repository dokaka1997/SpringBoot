package com.viettel.arpu.service.audit.impl;

import com.viettel.arpu.model.entity.SyncAuditLog;
import com.viettel.arpu.repository.SyncAuditRepository;
import com.viettel.arpu.service.audit.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {
    private final SyncAuditRepository syncAuditRepository;

    @Autowired
    public AuditLogServiceImpl(SyncAuditRepository syncAuditRepository) {
        this.syncAuditRepository = syncAuditRepository;
    }

    @Override
    public void save(SyncAuditLog syncAuditLog) {
        syncAuditRepository.save(syncAuditLog);
    }
}
