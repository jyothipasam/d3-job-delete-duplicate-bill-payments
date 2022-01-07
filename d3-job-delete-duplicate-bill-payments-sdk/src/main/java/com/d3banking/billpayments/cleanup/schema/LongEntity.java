package com.d3banking.billpayments.cleanup.schema;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Dummy Entity used by cleanup repositories which extend the
 * {@link org.springframework.data.repository.Repository} interface.
 */
@Data
@Entity
@Immutable
public class LongEntity {
    @Id
    @Column(name = "id")
    private Long id;
}