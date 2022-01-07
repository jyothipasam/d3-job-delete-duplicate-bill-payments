package com.d3banking.billpayments.cleanup.job;

import com.d3banking.billpayments.cleanup.service.CleanupStats;
import com.d3banking.billpayments.cleanup.service.M2TransferCleanupEventService;
import com.d3banking.billpayments.cleanup.service.M2TransferService;
import com.d3banking.test.assertions.MocksCollector;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

import static org.mockito.Mockito.when;

public class BillPaymentsCleanupJobTest {

    private BillPaymentsCleanupJob billPaymentsCleanupJob;

    @Mock
    private M2TransferService cleanupService;
    @Mock
    private M2TransferCleanupEventService eventService;

    private BillPaymentsCleanupJobProperties jobProperties;

    private MocksCollector mocks;

    @Mock
    private ExecutorService executorService;

    @Before
    public void setup() {
        mocks = new MocksCollector();
        MockitoAnnotations.initMocks(this);

        jobProperties = new BillPaymentsCleanupJobProperties();
        jobProperties.setAccountPageSize(1);
        jobProperties.setWaitTime(Duration.ofMillis(25L));

        //Practically the simplest executor possible: run the task when it is submitted -- in *your* thread.
        when(executorService.submit(Mockito.<Callable<CleanupStats>>any()))
            .thenAnswer((a) -> {
                FutureTask<CleanupStats> task = new FutureTask<>(a.getArgument(0));
                task.run();
                return task;
            });

        billPaymentsCleanupJob = new BillPaymentsCleanupJob(cleanupService, jobProperties, eventService, this.executorService);
    }
}
