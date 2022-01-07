package com.d3banking.config;

import static java.time.temporal.ChronoUnit.NANOS;

import com.d3banking.core.entity.D3Entity;
import com.d3banking.sdk.context.D3RequestContextHolder;
import com.d3banking.billpayments.cleanup.job.ExecutorServiceProperties;
import com.d3banking.billpayments.cleanup.job.BillPaymentsCleanupJobProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = {
    "com.d3banking.billpayments.cleanup.job",
})
@EnableConfigurationProperties(BillPaymentsCleanupJobProperties.class)
public class TransactionCleanupJobConfig {

    public TransactionCleanupJobConfig() {
        D3Entity.setActorSupplier(() -> D3RequestContextHolder.get().getActor());
    }

    @Bean
    public static ExecutorService transactionCleanupExecutorService(BillPaymentsCleanupJobProperties properties) {
        ExecutorServiceProperties executorProperties = properties.getExecutorService();
        int threadPoolSize = executorProperties.getThreadPoolSize();
        int poolSize = threadPoolSize <= 0 ? 1 : threadPoolSize;
        Duration keepAliveTime = executorProperties.getKeepAliveTime();
        int workQueueLength = executorProperties.getWorkQueueLength();
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(workQueueLength);
        RejectedExecutionHandler executionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

        return new ThreadPoolExecutor(poolSize, poolSize, keepAliveTime.get(NANOS), TimeUnit.NANOSECONDS, workQueue, executionHandler);
    }
}
