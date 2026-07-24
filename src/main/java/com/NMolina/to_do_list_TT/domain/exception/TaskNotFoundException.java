package com.NMolina.to_do_list_TT.domain.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("No se encontró la tarea con id: " + id);
    }
}
