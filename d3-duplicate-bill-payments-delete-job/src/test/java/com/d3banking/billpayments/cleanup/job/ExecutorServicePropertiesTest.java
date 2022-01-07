package com.d3banking.billpayments.cleanup.job;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.Duration;

public class ExecutorServicePropertiesTest {
    @Test
    public void testGetterClamping() {
        ExecutorServiceProperties properties = new ExecutorServiceProperties();
        properties.setKeepAliveTime(Duration.ofMillis(-5));
        properties.setThreadPoolSize(0);
        properties.setWorkQueueLength(-1);

        assertEquals(1, properties.getThreadPoolSize());
        assertEquals(0, properties.getWorkQueueLength());
        assertEquals(Duration.ZERO, properties.getKeepAliveTime());
    }
}
