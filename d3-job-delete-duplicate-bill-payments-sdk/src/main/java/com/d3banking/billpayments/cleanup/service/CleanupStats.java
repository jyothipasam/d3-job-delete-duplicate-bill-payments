package com.d3banking.billpayments.cleanup.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 * A container for collecting / summing record-deletion stats for a given run of the transaction cleanup job.
 * Supports summing, and translating to a Map for sending Entity state in an Event.
 * Objects of this type are immutable after construction.
 */
@Getter
@Builder
@Validated
public class CleanupStats {

    @Builder.Default
    private final int alertJournalPropCount = 0;

    @Builder.Default
    private final int alertJournalCount = 0;

    @Builder.Default
    private final int userTransactionCount = 0;

    @Builder.Default
    private final int userTransactionSplitCount = 0;

    @Builder.Default
    private final int transactionLocationCount = 0;

    @Builder.Default
    private final int transactionCount = 0;

    /**
     * Produce a new CleanupStats whose individual fields are the sum of the corresponding fields of the two summands.
     * @param other the other summand
     * @return a new CleanupStats, whose individual fields are the sum of this and the other summand's fields
     */
    public CleanupStats plus(@NotNull CleanupStats other) {

        return CleanupStats.builder()
            .alertJournalPropCount(alertJournalPropCount + other.getAlertJournalPropCount())
            .alertJournalCount(alertJournalCount + other.getAlertJournalCount())
            .userTransactionCount(userTransactionCount + other.getUserTransactionCount())
            .userTransactionSplitCount(userTransactionSplitCount + other.getUserTransactionSplitCount())
            .transactionLocationCount(transactionLocationCount + other.getTransactionLocationCount())
            .transactionCount(transactionCount + other.getTransactionCount())
            .build();
    }

    /**
     * Produce a Map representation of this CleanupStats, which is convenient for putting into an Entity state (when sending an Event)
     * @return a HashMap representation of this
     */
    public Map<String, Object> toMap() {
        Map<String, Object> calculatedStats = new HashMap<>();
        calculatedStats.put("alertJournalsDeleted", this.getAlertJournalCount());
        calculatedStats.put("alertJournalPropsDeleted", this.getAlertJournalPropCount());
        calculatedStats.put("transactionsDeleted", this.getTransactionCount());
        calculatedStats.put("transactionLocationsDeleted", this.getTransactionLocationCount());
        calculatedStats.put("userTransactionsDeleted", this.getUserTransactionCount());
        calculatedStats.put("userTransactionSplitsDeleted", this.getUserTransactionSplitCount());

        return calculatedStats;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof CleanupStats))
            return false;

        CleanupStats otherStats = (CleanupStats) other;
        return this.alertJournalCount == otherStats.alertJournalCount &&
            this.alertJournalPropCount == otherStats.alertJournalPropCount &&
            this.userTransactionCount == otherStats.userTransactionCount &&
            this.userTransactionSplitCount == otherStats.userTransactionSplitCount &&
            this.transactionLocationCount == otherStats.transactionLocationCount &&
            this.transactionCount == otherStats.transactionCount;
    }
}