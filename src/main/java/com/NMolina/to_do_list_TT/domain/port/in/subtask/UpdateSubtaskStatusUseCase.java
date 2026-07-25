package com.NMolina.to_do_list_TT.domain.port.in.subtask;

import com.NMolina.to_do_list_TT.domain.model.Subtask;

public interface UpdateSubtaskStatusUseCase {
    Subtask updateStatus(Long subtaskId, String newStatusCode, Long userId);
}