package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    Optional<Reference> findByMsisdn(String msisdn);
}
