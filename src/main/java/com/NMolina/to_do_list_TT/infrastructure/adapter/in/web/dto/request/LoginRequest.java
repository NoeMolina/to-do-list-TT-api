package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password) {
}