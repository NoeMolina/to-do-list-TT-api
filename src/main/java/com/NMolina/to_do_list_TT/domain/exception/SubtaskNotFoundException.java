package com.NMolina.to_do_list_TT.domain.exception;

public class SubtaskNotFoundException extends RuntimeException {
    public SubtaskNotFoundException(Long id) {
        super("No se encontró la subtarea con id: " + id);
    }
}