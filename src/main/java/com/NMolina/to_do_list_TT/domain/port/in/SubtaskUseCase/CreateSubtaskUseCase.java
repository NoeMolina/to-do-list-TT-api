package com.NMolina.to_do_list_TT.domain.port.in.SubtaskUseCase;

import com.NMolina.to_do_list_TT.domain.model.Subtask;

public interface CreateSubtaskUseCase {

    Subtask createSubtask(CreateSubtaskCommand command);

    record CreateSubtaskCommand(
            Long taskId,
            String title,
            String description) {
    }
}