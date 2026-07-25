package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank(message = "el título es obligatorio") @Size(max = 150) String title,
        String description,
        @FutureOrPresent(message = "la fecha de vencimiento no puede ser en el pasado") LocalDate dueDate,
        @NotNull(message = "la categoría es obligatoria") Integer categoryId,
        Long userId) {
}