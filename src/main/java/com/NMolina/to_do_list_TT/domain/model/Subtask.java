package com.NMolina.to_do_list_TT.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.NMolina.to_do_list_TT.domain.exception.InvalidStatusTransitionException;

public class Subtask {

    private static final Map<String, List<String>> VALID_TRANSITIONS = Map.of(
            "PENDIENTE", List.of("EN_PROGRESO", "CANCELADO"),
            "EN_PROGRESO", List.of("COMPLETADO", "CANCELADO", "PENDIENTE"),
            "COMPLETADO", List.of(),
            "CANCELADO", List.of());

    private Long id;
    private Long taskId;
    private String title;
    private String description;
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

    public Subtask(Long id, Long taskId, String title, String description, Status status,
            LocalDateTime createdAt, LocalDateTime updatedAt,
            Long createdBy, Long updatedBy) {
        this.id = id;
        this.taskId = Objects.requireNonNull(taskId, "taskId no puede ser nulo");
        this.title = validateTitle(title);
        this.description = description;
        this.status = Objects.requireNonNull(status, "status no puede ser nulo");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public static Subtask create(Long taskId, String title, String description, Status pendingStatus) {
        if (!pendingStatus.isCode("PENDIENTE")) {
            throw new IllegalArgumentException("Una subtarea nueva debe iniciar en estatus PENDIENTE");
        }
        return new Subtask(null, taskId, title, description, pendingStatus, null, null, null, null);
    }

    private String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título de la subtarea no puede estar vacío");
        }
        if (title.length() > 150) {
            throw new IllegalArgumentException("El título no puede exceder 150 caracteres");
        }
        return title;
    }

    public void changeStatus(Status newStatus) {
        List<String> allowed = VALID_TRANSITIONS.get(this.status.getCode());
        if (allowed == null || !allowed.contains(newStatus.getCode())) {
            throw new InvalidStatusTransitionException(
                    this.status.getCode(), newStatus.getCode());
        }
        this.status = newStatus;
    }

    public void updateDetails(String title, String description) {
        this.title = validateTitle(title);
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }
}