package com.viettel.arpu.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Table(name = "tbl_job_config")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class JobConfig {
    @Id
    private Long batchId;
    @NotNull
    private String cron;
    private String description;
}
