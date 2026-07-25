package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        CategoryResponse category,
        StatusResponse status,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}