package com.viettel.arpu.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Table(name = "tbl_sync_audit_log")
@Entity(name = "SyncAuditLog")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Data
@ToString
public class SyncAuditLog extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long batchId;
    @NotNull
    private Long recordNumber;
    @NotNull
    private String recordContent;
    @NotNull
    private String reason;

}
