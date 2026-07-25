package com.NMolina.to_do_list_TT.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Category category;
    private Status status;
    private Long userId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

    public Task(Long id, String title, String description, LocalDate dueDate,
            Category category, Status status, Long userId,
            LocalDateTime createdAt, LocalDateTime updatedAt,
            Long createdBy, Long updatedBy) {
        this.id = id;
        this.title = validateTitle(title);
        this.description = description;
        this.dueDate = dueDate;
        this.category = Objects.requireNonNull(category, "category no puede ser nula");
        this.status = Objects.requireNonNull(status, "status no puede ser nulo");
        this.userId = Objects.requireNonNull(userId, "userId no puede ser nulo");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    /** Factory method para crear una tarea nueva, siempre arranca en PENDIENTE. */
    public static Task create(String title, String description, LocalDate dueDate,
            Category category, Status pendingStatus, Long userId) {
        if (!pendingStatus.isCode("PENDIENTE")) {
            throw new IllegalArgumentException("Una tarea nueva debe iniciar en estatus PENDIENTE");
        }
        return new Task(null, title, description, dueDate, category,
                pendingStatus, userId, null, null, null, null);
    }

    private String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío");
        }
        if (title.length() > 150) {
            throw new IllegalArgumentException("El título no puede exceder 150 caracteres");
        }
        return title;
    }

    public void changeStatus(Status newStatus) {
        TaskLifecycle.validateTransition(this.status, newStatus);
        this.status = newStatus;
    }

    public void updateDetails(String title, String description,
            LocalDate dueDate, Category category) {
        this.title = validateTitle(title);
        this.description = description;
        this.dueDate = dueDate;
        this.category = Objects.requireNonNull(category);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Category getCategory() {
        return category;
    }

    public Status getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
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