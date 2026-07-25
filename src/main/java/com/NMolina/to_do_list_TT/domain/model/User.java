package com.NMolina.to_do_list_TT.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private Long id;
    private String username;
    private String passwordHash;
    private boolean active;
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

    public User(Long id, String username, String passwordHash, boolean active, Role role,
            LocalDateTime createdAt, LocalDateTime updatedAt,
            Long createdBy, Long updatedBy) {
        this.id = id;
        this.username = validateUsername(username);
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash no puede ser nulo");
        this.active = active;
        this.role = Objects.requireNonNull(role, "rol no puede ser nulo");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public static User register(String username, String passwordHash, Role role) {
        return new User(null, username, passwordHash, true, role, null, null, null, null);
    }

    private String validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El username no puede estar vacío");
        }
        if (username.length() > 50) {
            throw new IllegalArgumentException("El username no puede exceder 50 caracteres");
        }
        return username;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash, "passwordHash no puede ser nulo");
    }

    public boolean isAdmin() {return role.isAdmin();}

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public Role getRole() {
        return role;
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