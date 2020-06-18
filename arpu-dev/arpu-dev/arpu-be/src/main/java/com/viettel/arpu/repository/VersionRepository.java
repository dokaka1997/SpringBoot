package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.Version;
import com.viettel.arpu.model.entity.VersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version, VersionId>, VersionRepositoryCustom {

}
