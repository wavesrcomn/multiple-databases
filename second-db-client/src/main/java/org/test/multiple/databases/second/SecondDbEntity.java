package org.test.multiple.databases.second;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table("gates")
@Accessors(chain = true)
@Builder(toBuilder = true)
@FieldNameConstants
public class SecondDbEntity {
    @Column("gate_id")
    @Id
    UUID gateId;
    @Column("gate_name")
    String gateName;
    @Column("gate_type")
    String gateType;
    @Column("gate_url")
    String gateUrl;
    Instant created;
    Instant deleted;
}
