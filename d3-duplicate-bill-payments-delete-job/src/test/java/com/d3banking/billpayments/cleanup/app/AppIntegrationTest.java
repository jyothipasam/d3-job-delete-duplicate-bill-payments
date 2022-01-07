package com.d3banking.billpayments.cleanup.app;

import static org.junit.Assert.assertNotNull;

import com.d3banking.billpayments.cleanup.job.BillPaymentsCleanupJob;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BillPaymentsCleanupApp.class},
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE)
public class AppIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void healthCheck() {
        BillPaymentsCleanupJob job = applicationContext.getBean(BillPaymentsCleanupJob.class);

        assertNotNull(job);

        ExecutorService executorService = applicationContext.getBean(ExecutorService.class);

        assertNotNull(executorService);
    }

}
