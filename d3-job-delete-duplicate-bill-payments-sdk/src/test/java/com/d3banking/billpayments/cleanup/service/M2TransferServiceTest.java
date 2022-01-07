package com.d3banking.billpayments.cleanup.service;

import com.d3banking.billpayments.cleanup.repository.M2TransferRepository;
import com.d3banking.test.assertions.MocksCollector;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.Collections;

public class M2TransferServiceTest {
    private static final Slice<Number> EMPTY_SLICE = new SliceImpl<>(Collections.emptyList());
    private M2TransferService transactionCleanupService;
    @Mock
    private M2TransferRepository transactionRepository;
    private MocksCollector mocks;

    @Before
    public void setup() {
        mocks = new MocksCollector();
        MockitoAnnotations.initMocks(this);

        transactionCleanupService = new M2TransferService(transactionRepository);
    }
}
