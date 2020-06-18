package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.SyncAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncAuditRepository extends JpaRepository<SyncAuditLog, Long>{

}
