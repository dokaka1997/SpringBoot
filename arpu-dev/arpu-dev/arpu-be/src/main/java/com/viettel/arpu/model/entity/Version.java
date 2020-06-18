package com.viettel.arpu.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Version")
@Table(name = "tbl_version")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Version {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private VersionId versionId;
    private String batchName;
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();
}
