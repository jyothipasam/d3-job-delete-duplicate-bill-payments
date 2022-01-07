package com.d3banking.billpayments.cleanup.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.d3banking.config")
public class BillPaymentsCleanupApp {
    public static void main(String[] args) {
        SpringApplication.run(BillPaymentsCleanupApp.class, args);
    }
}
