package org.test.multiple.databases.first;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("account")
@Accessors(chain = true)
@Builder(toBuilder = true)
@FieldNameConstants
public class FirstDBEntity {
    @Id
    @Column("account_id")
    String accountId;
    @Column("is_closed")
    Boolean isClosed;
}
