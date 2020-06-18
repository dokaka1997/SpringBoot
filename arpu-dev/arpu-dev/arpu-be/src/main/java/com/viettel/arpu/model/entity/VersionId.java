package com.viettel.arpu.model.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class VersionId implements Serializable {
    private Long version;
    private Long batchId;
}
