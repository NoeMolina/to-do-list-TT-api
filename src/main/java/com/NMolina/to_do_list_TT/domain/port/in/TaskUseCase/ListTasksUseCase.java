package com.NMolina.to_do_list_TT.domain.port.in.TaskUseCase;

import java.util.List;

import com.NMolina.to_do_list_TT.domain.model.Task;

public interface ListTasksUseCase {
    List<Task> listByUser(Long userId);
}