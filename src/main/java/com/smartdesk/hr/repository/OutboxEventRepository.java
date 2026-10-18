package com.smartdesk.hr.repository;

import com.smartdesk.hr.entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {

	List<OutboxEventEntity> 
	findTop100ByProcessedAtIsNullAndNextRetryAtBeforeOrderByCreatedAtAsc(Instant now);
}
