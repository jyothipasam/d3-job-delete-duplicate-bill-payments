package com.d3banking.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.d3banking.billpayments.cleanup.repository")
@EntityScan(basePackages = "com.d3banking.billpayments.cleanup.schema")
@ComponentScan(basePackages = {
    "com.d3banking.billpayments.cleanup.service"
})
public class BillPaymentsCleanupSdkConfig {
}
