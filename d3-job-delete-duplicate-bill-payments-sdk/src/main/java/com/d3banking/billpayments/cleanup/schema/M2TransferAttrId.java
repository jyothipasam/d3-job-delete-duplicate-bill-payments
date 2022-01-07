package com.d3banking.billpayments.cleanup.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class M2TransferAttrId implements Serializable {

    private String attrName;
    private long transferId;
}
