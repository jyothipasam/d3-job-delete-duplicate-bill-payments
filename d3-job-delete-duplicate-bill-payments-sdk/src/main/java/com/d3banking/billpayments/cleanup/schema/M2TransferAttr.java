package com.d3banking.billpayments.cleanup.schema;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Builder
@Data
@Entity
@IdClass(M2TransferAttrId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m2_transfer_attr")
@Immutable
public class M2TransferAttr {
    @Id
    @Column(name = "attrName")
    private String attrName;
    @Id
    @Column(name = "transferId")
    private long transferId;
    private String modifiedBy;
    private String modifiedByType;
    private java.sql.Timestamp createdTs;
    private String deleted;
    private java.sql.Timestamp deletedTs;
    private java.sql.Timestamp updatedTs;
    private long version;
    private String attrValue;

}
