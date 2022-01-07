package com.d3banking.billpayments.cleanup.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

@ConfigurationProperties(prefix = "d3.job.cleanup.transaction")
@Data
public class BillPaymentsCleanupJobProperties {
    /**
     * Fetch page size when iterating over accounts whose transactions to clean up.
     */
    private int accountPageSize;

    /**
     * Time to wait after cleaning up transactions before starting on the next page.
     */
    private Duration waitTime;

    /**
     * Contains the (nested) properties used to configure the transaction cleanup executor service.
     */
    @NestedConfigurationProperty
    private ExecutorServiceProperties executorService;

    public int getAccountPageSize() {
        return Math.max(1, this.accountPageSize);
    }

    public Duration getWaitTime() {
        return (waitTime == null || waitTime.isNegative()) ? Duration.ZERO : waitTime;
    }
}
