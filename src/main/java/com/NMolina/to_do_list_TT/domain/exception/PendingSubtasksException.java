package com.NMolina.to_do_list_TT.domain.exception;

public class PendingSubtasksException extends RuntimeException {
    public PendingSubtasksException(Long taskId){
        super("No se puede completar la tarea " + taskId +": tiene subtareas pendientes o en progreso");
    }
}
