package com.bushido.challenge.services.audit;

import com.bushido.challenge.entities.AuditLog;
import com.bushido.challenge.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logUserActivity(UUID userId, String operation) {
        log.info("ASYNC AUDIT: Logging activity for User ID: {}, Operation: {}", userId, operation);
        try {
            AuditLog logEntry = new AuditLog(
                    "User",
                    operation,
                    userId,
                    LocalDateTime.now()
            );
            auditLogRepository.save(logEntry);
            log.info("ASYNC AUDIT: Successfully saved audit log for User ID: {}", userId);
        } catch (Exception e) {
             log.error("ASYNC AUDIT: Failed to save audit log for User ID: {}. Error: {}",
                    userId, e.getMessage(), e);
         }
    }
}