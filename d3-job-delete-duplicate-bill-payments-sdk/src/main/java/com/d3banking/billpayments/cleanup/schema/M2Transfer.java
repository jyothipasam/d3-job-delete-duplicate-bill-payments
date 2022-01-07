package com.d3banking.billpayments.cleanup.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m2_transfer")
@Immutable
public class M2Transfer {
  @Id
  @Column(name = "id")
  private long id;
  private String modifiedBy;
  private String modifiedByType;
  private java.sql.Timestamp createdTs;
  private String deleted;
  private java.sql.Timestamp deletedTs;
  private java.sql.Timestamp updatedTs;
  private long version;
  private double amount;
  private String approvalItemId;
  private String autoPay;
  private String destEndpointName;
  private String digest;
  private String externalId;
  private String memo;
  private String note;
  private String recurring;
  private java.sql.Date scheduledDate;
  private String sourceEndpointName;
  private String status;
  private String statusFailedReason;
  private String statusText;
  private double thresholdAmount;
  private long userId;
  private long destEndpointProviderId;
  private long destUserAccountId;
  private long ebillId;
  private String providerName;
  private long providerOptionId;
  private long recurringId;
  private long sourceEndpointProviderId;
  private long sourceUserAccountId;
  private long worksheetId;

}
