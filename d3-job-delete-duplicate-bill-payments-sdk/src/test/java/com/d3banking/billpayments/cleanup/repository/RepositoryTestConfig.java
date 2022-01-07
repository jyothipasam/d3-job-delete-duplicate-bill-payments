package com.d3banking.billpayments.cleanup.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.d3banking.billpayments.cleanup.schema"})
@EnableJpaRepositories(basePackages = {"com.d3banking.billpayments.cleanup.repository"})
public class RepositoryTestConfig {
}
