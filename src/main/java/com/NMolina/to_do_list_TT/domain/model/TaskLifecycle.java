package com.NMolina.to_do_list_TT.domain.model;

import java.util.List;
import java.util.Map;

import com.NMolina.to_do_list_TT.domain.exception.InvalidStatusTransitionException;

public final class TaskLifecycle {

    private static final Map<String, List<String>> VALID_TRANSITIONS = Map.of(
            "PENDIENTE", List.of("EN_PROGRESO", "CANCELADO"),
            "EN_PROGRESO", List.of("COMPLETADO","CANCELADO"),
            "COMPLETADO", List.of(),
            "CANCELADO", List.of());

    private TaskLifecycle() {
    }

    public static void validateTransition(Status current, Status target) {
        List<String> allowed = VALID_TRANSITIONS.get(current.getCode());
        if (allowed == null || !allowed.contains(target.getCode())) {
            throw new InvalidStatusTransitionException(current.getCode(), target.getCode());
        }
    }
}