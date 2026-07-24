package com.NMolina.to_do_list_TT.domain.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String currentStatus, String targetStatus) {
        super("No se puede cambiar de '%s' a '%s'".formatted(currentStatus, targetStatus));
    }
}