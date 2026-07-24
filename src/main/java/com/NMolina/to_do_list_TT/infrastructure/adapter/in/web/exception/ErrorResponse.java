package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> details) {
}