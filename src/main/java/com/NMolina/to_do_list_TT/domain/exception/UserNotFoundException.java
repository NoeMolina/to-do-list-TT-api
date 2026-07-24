package com.NMolina.to_do_list_TT.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) {
        super("No se encontró el usuario: " + identifier);
    }
}