package com.NMolina.to_do_list_TT.domain.port.in.subtask;

import java.util.List;

import com.NMolina.to_do_list_TT.domain.model.Subtask;

public interface ListSubtasksUseCase {
    List<Subtask> listByTask(Long taskId, Long userId, boolean isAdmin);
}