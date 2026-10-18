package com.smartdesk.hr.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdesk.hr.entity.HrUserEntity;
import com.smartdesk.hr.entity.OutboxEventEntity;
import com.smartdesk.hr.repository.HrUserRepository;
import com.smartdesk.hr.repository.OutboxEventRepository;
import com.yourorg.core.kafka.event.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HrUserServiceImpl implements HrUserService {

    private final HrUserRepository hrUserRepository;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "hr.user.created.v1";

    @Transactional
    @Override
    public HrUserEntity createUser(HrUserEntity entity) throws Exception {

        // 1️⃣ Save business entity
        HrUserEntity saved = hrUserRepository.save(entity);

        // 2️⃣ Build event
        EventMetadata metadata = new EventMetadata(
                UUID.randomUUID(),
                "HR_USER_CREATED",
                "v1",
                Instant.now(),
                "hr-service"
        );

        HrUserCreatedPayload payload = new HrUserCreatedPayload(
                saved.getTenantCode(),
                saved.getEmployeeId(),
                saved.getEmail(),
                saved.getDisplayName(),
                saved.getUserType(),
                saved.getStatus(),
                saved.getTerminatedAt(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

        HrUserCreatedEvent event =
                new HrUserCreatedEvent(metadata, payload);

        // 3️⃣ Save to outbox in SAME transaction
        OutboxEventEntity outbox = new OutboxEventEntity();
        outbox.setTopic(TOPIC);
        outbox.setAggregateId(saved.getEmployeeId());
        outbox.setPayload(objectMapper.writeValueAsString(event));

        outboxRepository.save(outbox);

        return saved;
    }
}
