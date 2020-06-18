package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.CustomerRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRefRepository extends JpaRepository<CustomerRef, Long> {
}
