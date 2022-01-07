package com.d3banking.billpayments.cleanup.job;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.Duration;

public class BillPaymentsCleanupJobPropertiesTest {

    @Test
    public void testClamping() {
        BillPaymentsCleanupJobProperties properties = new BillPaymentsCleanupJobProperties();
        properties.setAccountPageSize(-4);
        properties.setWaitTime(Duration.ofMillis(-4));

        assertEquals(Duration.ZERO, properties.getWaitTime());
        assertEquals(1, properties.getAccountPageSize());
    }

    @Test
    public void testNullClamping() {
        BillPaymentsCleanupJobProperties properties = new BillPaymentsCleanupJobProperties();
        properties.setAccountPageSize(-4);
        properties.setWaitTime(null);

        assertEquals(Duration.ZERO, properties.getWaitTime());
        assertEquals(1, properties.getAccountPageSize());
    }
}
