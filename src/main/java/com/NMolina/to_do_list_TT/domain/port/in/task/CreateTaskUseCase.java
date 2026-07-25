package com.NMolina.to_do_list_TT.domain.port.in.task;

import java.time.LocalDate;

import com.NMolina.to_do_list_TT.domain.model.Task;

public interface CreateTaskUseCase {

    Task createTask(CreateTaskCommand command);

    record CreateTaskCommand(
            String title,
            String description,
            LocalDate dueDate,
            Integer categoryId,
            Long userId) {
    }
}