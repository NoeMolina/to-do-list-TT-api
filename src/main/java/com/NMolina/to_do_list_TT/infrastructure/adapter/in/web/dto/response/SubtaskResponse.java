package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDateTime;

public record SubtaskResponse(
        Long id,
        Long taskId,
        String title,
        String description,
        StatusResponse status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}