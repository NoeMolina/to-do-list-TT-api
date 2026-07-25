package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.CategoryResponse;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.StatusResponse;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.TaskResponse;

@Component
public class TaskWebMapper {

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                new CategoryResponse(task.getCategory().getId(), task.getCategory().getCode(),
                        task.getCategory().getName()),
                new StatusResponse(task.getStatus().getId(), task.getStatus().getCode(), task.getStatus().getName()),
                task.getUserId(),
                task.getCreatedAt(),
                task.getUpdatedAt());
    }
}