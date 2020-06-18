package com.viettel.arpu.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class VersionRepositoryImpl implements VersionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Long getLatestVersionForBatchId(Long batchId) {
        try {
            Query query = entityManager
                    .createQuery("select max(v.versionId.version) from Version v where v.versionId.batchId = :batchId");
            query.setParameter("batchId", batchId);
            Object result = query.getSingleResult();
            return Optional.ofNullable((Long) result).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
