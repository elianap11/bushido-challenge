package com.bushido.challenge.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String entityType;

    @Column(nullable = false, updatable = false)
    private String operation;

    @Column(nullable = false, updatable = false)
    private UUID entityId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    public AuditLog(String entityType, String operation, UUID entityId, LocalDateTime timestamp) {
        this.entityType = entityType;
        this.operation = operation;
        this.entityId = entityId;
        this.timestamp = timestamp;
    }
}