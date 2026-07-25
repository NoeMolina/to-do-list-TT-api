package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NMolina.to_do_list_TT.domain.model.Subtask;
import com.NMolina.to_do_list_TT.domain.port.in.subtask.CreateSubtaskUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.subtask.UpdateSubtaskStatusUseCase;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request.CreateSubtaskRequest;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request.UpdateTaskStatusRequest;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.SubtaskResponse;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.mapper.SubtaskWebMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.security.UserDetailsAdapter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subtasks")
public class SubtaskController {

    private final CreateSubtaskUseCase createSubtaskUseCase;
    private final UpdateSubtaskStatusUseCase updateSubtaskStatusUseCase;
    private final SubtaskWebMapper mapper;

    public SubtaskController(CreateSubtaskUseCase createSubtaskUseCase,
            UpdateSubtaskStatusUseCase updateSubtaskStatusUseCase,
            SubtaskWebMapper mapper) {
        this.createSubtaskUseCase = createSubtaskUseCase;
        this.updateSubtaskStatusUseCase = updateSubtaskStatusUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<SubtaskResponse> create(@Valid @RequestBody CreateSubtaskRequest request) {
        Subtask subtask = createSubtaskUseCase.createSubtask(new CreateSubtaskUseCase.CreateSubtaskCommand(
                request.taskId(),
                request.title(),
                request.description()));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(subtask));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SubtaskResponse> updateStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request,
            Authentication authentication) {
        Long currentUserId = ((UserDetailsAdapter) authentication.getPrincipal()).getUserId();
        Subtask subtask = updateSubtaskStatusUseCase.updateStatus(id, request.status(), currentUserId);
        return ResponseEntity.ok(mapper.toResponse(subtask));
    }
}