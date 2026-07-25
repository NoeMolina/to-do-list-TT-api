package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSubtaskRequest(
        @NotNull(message = "el taskId es obligatorio") Long taskId,
        @NotBlank(message = "el título es obligatorio") @Size(max = 150) String title,
        String description) {
}