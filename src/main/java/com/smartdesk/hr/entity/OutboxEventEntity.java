package com.smartdesk.hr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_event")
@Getter
@Setter
public class OutboxEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant processedAt;

    @Column(nullable = false)
    private int retryCount = 0;

    @Column
    private Instant nextRetryAt;

    @Column
    private String lastError;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        nextRetryAt = createdAt;
    }
}
