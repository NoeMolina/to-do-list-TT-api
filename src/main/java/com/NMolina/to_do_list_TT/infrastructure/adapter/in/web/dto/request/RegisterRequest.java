package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "el username es obligatorio") @Size(max = 50) String username,
        @NotBlank(message = "el password es obligatorio") @Size(min = 8, message = "mínimo 8 caracteres") String password) {
}