package com.NMolina.to_do_list_TT.domain.port.in.task;

import com.NMolina.to_do_list_TT.domain.model.Task;

public interface UpdateTaskStatusUseCase {
    Task updateStatus(Long taskId, String newStatusCode, Long userId, boolean isAdmin);
}