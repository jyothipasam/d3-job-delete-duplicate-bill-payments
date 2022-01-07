package com.d3banking.billpayments.cleanup.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.d3banking.job.sdk.D3JobContextProperties;
import com.d3banking.job.sdk.JobRequestContext;
import com.d3banking.sdk.context.D3RequestContextHolder;
import com.d3banking.sdk.event.Action;
import com.d3banking.sdk.event.ActionStatus;
import com.d3banking.sdk.event.ActionType;
import com.d3banking.sdk.event.Entity;
import com.d3banking.sdk.event.Event;
import com.d3banking.sdk.event.service.EventService;
import com.d3banking.test.assertions.MocksCollector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

public class M2TransferCleanupEventServiceTest {
    private M2TransferCleanupEventService transactionCleanupEventService;

    @Mock
    private EventService eventService;
    private D3JobContextProperties contextProperties;

    private MocksCollector mocks;

    @Before
    public void setup() {
        mocks = new MocksCollector();
        MockitoAnnotations.initMocks(this);

        D3RequestContextHolder.set(JobRequestContext.builder()
            .uuid(UUID.randomUUID())
            .build());

        contextProperties = new D3JobContextProperties();
        transactionCleanupEventService = new M2TransferCleanupEventService(eventService, contextProperties);
    }

    @Test
    public void testSendJobCompleteEvent() {
        CleanupStats stats = CleanupStats.builder()
            .transactionCount(1)
            .transactionLocationCount(1)
            .userTransactionSplitCount(2)
            .userTransactionCount(2)
            .alertJournalCount(4)
            .alertJournalPropCount(8)
            .build();

        transactionCleanupEventService.sendJobStatsEvent(stats);

        Action expectedAction = D3RequestContextHolder.actionBuilder()
            .entity(Entity.builder()
                .id(Optional.of(D3RequestContextHolder.get().getUuid())
                    .map(UUID::toString)
                    .orElse("billPaymentsCleanupJob"))
                .stateAfter(stats.toMap())
                .build())
            .name("M2_TRANSFER_DUPLICATE_CLEANUP_JOB_STATS")
            .status(ActionStatus.SUCCESS)
            .type(ActionType.UNKNOWN)
            .subcomponent("M2_TRANSFER_DUPLICATE_CLEANUP_JOB")
            .application(contextProperties.getApplication())
            .component(contextProperties.getComponent())
            .build();

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        verify(eventService).broadcast(eventCaptor.capture());

        Event capturedEvent = eventCaptor.getValue();

        assertEquals(expectedAction, capturedEvent.getAction());

        mocks.verifyNoMoreInteractions();
    }
}
