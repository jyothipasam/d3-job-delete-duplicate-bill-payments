package com.d3banking.billpayments.cleanup.job;

import lombok.Data;

import java.time.Duration;

@Data
public class ExecutorServiceProperties {
    private int threadPoolSize;

    private Duration keepAliveTime;

    private int workQueueLength = 250;

    public int getThreadPoolSize() {
        return Math.max(1, threadPoolSize);
    }

    public Duration getKeepAliveTime() {
        return (keepAliveTime == null || keepAliveTime.isNegative()) ? Duration.ZERO : keepAliveTime;
    }

    public int getWorkQueueLength() {
        return Math.max(0, workQueueLength);
    }
}
