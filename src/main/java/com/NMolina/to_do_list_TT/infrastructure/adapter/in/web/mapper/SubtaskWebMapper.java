package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Subtask;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.StatusResponse;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.SubtaskResponse;

@Component
public class SubtaskWebMapper {

    public SubtaskResponse toResponse(Subtask subtask) {
        return new SubtaskResponse(
                subtask.getId(),
                subtask.getTaskId(),
                subtask.getTitle(),
                subtask.getDescription(),
                new StatusResponse(
                        subtask.getStatus().getId(),
                        subtask.getStatus().getCode(),
                        subtask.getStatus().getName()),
                subtask.getCreatedAt(),
                subtask.getUpdatedAt());
    }
}