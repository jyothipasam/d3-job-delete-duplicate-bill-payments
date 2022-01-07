package com.d3banking.billpayments.cleanup.service;

import com.d3banking.billpayments.cleanup.repository.M2TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@Slf4j
public class M2TransferService {

    private final M2TransferRepository transactionRepository;

    public M2TransferService(M2TransferRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public int deleteM2TransferTransactions(){
        return transactionRepository.deleteM2TransferTransactions();
    }

    public int deleteM2TransferAttrTransactions(){
        return transactionRepository.deleteM2TransferAttrTransactions();
    }

}
