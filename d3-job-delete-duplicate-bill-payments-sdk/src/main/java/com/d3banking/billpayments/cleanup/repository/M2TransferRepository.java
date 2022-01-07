package com.d3banking.billpayments.cleanup.repository;

import com.d3banking.billpayments.cleanup.schema.LongEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;

@Validated
public interface M2TransferRepository extends Repository<LongEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete FROM m2_transfer WHERE id IN (SELECT maxId FROM (SELECT max(t.id) as maxId FROM m2_transfer t WHERE t.external_id IS NOT NULL GROUP BY t.user_id, t.external_id HAVING count(*) > 1) AS m2t)",
            nativeQuery = true)
    int deleteM2TransferTransactions();

    @Transactional
    @Modifying
    @Query(value = "delete FROM m2_transfer_attr WHERE transfer_id IN (SELECT maxId FROM (SELECT max(t.id) as maxId FROM m2_transfer t WHERE t.external_id IS NOT NULL GROUP BY t.user_id, t.external_id HAVING count(*) > 1) AS m2t)",
            nativeQuery = true)
    int deleteM2TransferAttrTransactions();
}
