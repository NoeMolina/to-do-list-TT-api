package com.NMolina.to_do_list_TT.domain.exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String username) {
        super("El nombre de usuario '%s' ya está en uso".formatted(username));
    }
}

