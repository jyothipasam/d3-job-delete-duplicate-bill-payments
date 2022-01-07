package com.d3banking.billpayments.cleanup.job;

import com.d3banking.billpayments.cleanup.service.CleanupStats;
import com.d3banking.billpayments.cleanup.service.M2TransferCleanupEventService;
import com.d3banking.billpayments.cleanup.service.M2TransferService;
import com.d3banking.job.D3Job;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@Slf4j
@D3Job
public class BillPaymentsCleanupJob {

    private final M2TransferService m2TransferService;
    private final BillPaymentsCleanupJobProperties billPaymentsCleanupJobProperties;
    private final M2TransferCleanupEventService eventService;
    private final ExecutorService executorService;

    private static final long NANOS_PER_MILLI = 1_000_000L;

    public BillPaymentsCleanupJob(M2TransferService transactionCleanupService,
                                  BillPaymentsCleanupJobProperties billPaymentsCleanupJobProperties, M2TransferCleanupEventService eventService, ExecutorService executorService) {
        this.m2TransferService = transactionCleanupService;
        this.billPaymentsCleanupJobProperties = billPaymentsCleanupJobProperties;
        this.eventService = eventService;
        this.executorService = executorService;
    }

    @D3Job.Handler
    public void execute() throws InterruptedException {

        long startTime = System.nanoTime();
        int m2TransferTransactionDeleted = m2TransferService.deleteM2TransferTransactions();
        int m2TransferAttrTransactionDeleted = m2TransferService.deleteM2TransferAttrTransactions();
        CleanupStats overallStats = CleanupStats.builder().build();
        long totalTime = System.nanoTime() - startTime;

        log.info("Completed in {}. Deleted {} transactions, {} alert journal properties, {} alert journals, "
                + "{} user transactions, {} user transaction splits, and {} transaction locations",
            DurationFormatUtils.formatDurationHMS(totalTime / NANOS_PER_MILLI), overallStats.getTransactionCount(), overallStats.getAlertJournalPropCount(),
            overallStats.getAlertJournalCount(), overallStats.getUserTransactionCount(),
            overallStats.getUserTransactionSplitCount(), overallStats.getTransactionLocationCount());

        eventService.sendJobStatsEvent(overallStats);
    }

    private CleanupStats resolveFuture(Future<CleanupStats> future) {
        CleanupStats stats = null;

        try {
            stats = future.get();
        } catch (InterruptedException e) {
            log.error("Transaction Cleanup task interrupted: ", e);
        } catch (ExecutionException e) {
            log.error("Transaction cleanup task failed: ", e);
        }

        return stats;
    }

}