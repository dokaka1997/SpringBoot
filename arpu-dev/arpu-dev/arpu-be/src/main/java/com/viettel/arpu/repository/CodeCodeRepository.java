package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.CodeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeCodeRepository extends JpaRepository<CodeCode, String> {
}
