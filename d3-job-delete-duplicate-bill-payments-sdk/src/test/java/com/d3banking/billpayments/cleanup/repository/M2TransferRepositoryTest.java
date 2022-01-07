package com.d3banking.billpayments.cleanup.repository;

import com.d3banking.billpayments.cleanup.schema.M2Transfer;
import com.d3banking.billpayments.cleanup.schema.M2TransferAttr;
import com.d3banking.config.CoreDomainConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CoreDomainConfig.class, RepositoryTestConfig.class})
@DataJpaTest
@Slf4j
public class M2TransferRepositoryTest {

    @Autowired
    private M2TransferRepository transactionRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    private M2Transfer transactionOne;
    private M2Transfer transactionTwo;
    private M2Transfer transactionThree;

    private M2TransferAttr m2TransferAttr1;

    @Test
    public void testDeleteDuplicateM2TransferTransactions() {
        transactionRepository.deleteM2TransferTransactions();
    }

    @Before
    public void setup() {
        transactionOne = testEntityManager.persist(M2Transfer.builder().id(111).externalId("ExternalId").userId(9999).build());
        transactionTwo = testEntityManager.persist(M2Transfer.builder().id(222).externalId("ExternalId").userId(9999).build());
        transactionThree = testEntityManager.persist(M2Transfer.builder().id(333).externalId("SomeotherExternalId").userId(888).build());

        m2TransferAttr1 = testEntityManager.persist(M2TransferAttr.builder().transferId(222).attrName("attrName").build());
    }

    @Test
    public void testDeleteTransactionsByTransactionIds() {
        int deleted = transactionRepository.deleteM2TransferTransactions();
        assertEquals(1, deleted);

        assertThrows(EntityNotFoundException.class,
                () -> testEntityManager.refresh(transactionTwo));
    }


    @Test
    public void testDeleteM2TransferAttrTransactions() {
        int deleted = transactionRepository.deleteM2TransferAttrTransactions();
        assertEquals(1, deleted);

        assertThrows(EntityNotFoundException.class,
                () -> testEntityManager.refresh(m2TransferAttr1));
    }

}