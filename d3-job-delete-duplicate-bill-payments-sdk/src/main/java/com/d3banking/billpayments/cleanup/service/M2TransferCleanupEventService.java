package com.d3banking.billpayments.cleanup.service;

import com.d3banking.job.sdk.D3JobContextProperties;
import com.d3banking.sdk.context.D3RequestContextHolder;
import com.d3banking.sdk.event.ActionStatus;
import com.d3banking.sdk.event.ActionType;
import com.d3banking.sdk.event.Entity;
import com.d3banking.sdk.event.service.EventService;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class M2TransferCleanupEventService {

    private final EventService eventService;
    private final D3JobContextProperties contextProperties;

    public M2TransferCleanupEventService(EventService eventService, D3JobContextProperties contextProperties) {
        this.eventService = eventService;
        this.contextProperties = contextProperties;
    }

    /**
     * Send an event indicating deleted-record count stats for this job; to be sent once before job end.
     * @param stats the CleanupStats object holding deleted-record count stats for this job
     */
    public void sendJobStatsEvent(CleanupStats stats) {
        Entity entity = buildEntity(stats);

        sendEvent(entity, "M2_TRANSFER_DUPLICATE_CLEANUP_JOB_STATS", ActionStatus.SUCCESS);
    }

    private Entity buildEntity(CleanupStats stats) {
        return Entity.builder()
            .id(Optional.of(D3RequestContextHolder.get().getUuid())
                .map(UUID::toString)
                .orElse("billPaymentsCleanupJob"))
            .stateAfter(stats.toMap())
            .build();
    }

    private void sendEvent(Entity entity, String auditName, ActionStatus status) {
        eventService.broadcast(
            D3RequestContextHolder.eventBuilder()
                .action(D3RequestContextHolder.actionBuilder()
                    .entity(entity)
                    .name(auditName)
                    .status(status)
                    .type(ActionType.UNKNOWN)
                    .subcomponent("M2_TRANSFER_DUPLICATE_CLEANUP_JOB")
                    .application(contextProperties.getApplication())
                    .component(contextProperties.getComponent())
                    .build())
                .build()
        );
    }
}
