package com.smartdesk.hr.kafka.publisher;

import java.time.Instant;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartdesk.hr.entity.OutboxEventEntity;
import com.smartdesk.hr.repository.OutboxEventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HrUserEventPublisher {

    private final OutboxEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int MAX_RETRY = 5;

    @Scheduled(fixedDelay = 5000)
    public void publishOutboxEvents() {

        List<OutboxEventEntity> events =
                repository.findTop100ByProcessedAtIsNullAndNextRetryAtBeforeOrderByCreatedAtAsc(
                        Instant.now()
                );

        for (OutboxEventEntity event : events) {

            try {

                kafkaTemplate.send(
                        event.getTopic(),
                        event.getAggregateId(),
                        event.getPayload()
                ).get(); // wait for ACK

                event.setProcessedAt(Instant.now());
                repository.save(event);

            } catch (Exception ex) {

                handleFailure(event, ex);
            }
        }
    }

    private void handleFailure(OutboxEventEntity event, Exception ex) {

        int retry = event.getRetryCount() + 1;
        event.setRetryCount(retry);
        event.setLastError(ex.getMessage());

        if (retry >= MAX_RETRY) {

            log.error("Max retry reached for event {}", event.getId());

            // Send to Dead Letter Topic
            kafkaTemplate.send(
                    event.getTopic() + ".DLT",
                    event.getAggregateId(),
                    event.getPayload()
            );

            event.setProcessedAt(Instant.now());
        } else {

            // Exponential Backoff
            long delaySeconds = (long) Math.pow(2, retry);
            event.setNextRetryAt(Instant.now().plusSeconds(delaySeconds));

            log.warn("Retry {} scheduled after {} sec for event {}",
                    retry, delaySeconds, event.getId());
        }

        repository.save(event);
    }
}
