package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.domain.port.in.task.CreateTaskUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.task.ListTasksUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.task.UpdateTaskStatusUseCase;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request.CreateTaskRequest;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request.UpdateTaskStatusRequest;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.TaskResponse;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.mapper.TaskWebMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.security.UserDetailsAdapter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;
    private final ListTasksUseCase listTasksUseCase;
    private final TaskWebMapper mapper;

    public TaskController(CreateTaskUseCase createTaskUseCase,
            UpdateTaskStatusUseCase updateTaskStatusUseCase,
            ListTasksUseCase listTasksUseCase,
            TaskWebMapper mapper) {
        this.createTaskUseCase = createTaskUseCase;
        this.updateTaskStatusUseCase = updateTaskStatusUseCase;
        this.listTasksUseCase = listTasksUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request,
            Authentication authentication) {
        Long currentUserId = extractUserId(authentication);
        boolean isAdmin = isAdmin(authentication);

        Long ownerId = (isAdmin && request.userId() != null) ? request.userId() : currentUserId;

        Task task = createTaskUseCase.createTask(new CreateTaskUseCase.CreateTaskCommand(
                request.title(),
                request.description(),
                request.dueDate(),
                request.categoryId(),
                ownerId));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(task));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request,
            Authentication authentication) {
        Long currentUserId = extractUserId(authentication);
        boolean isAdmin = isAdmin(authentication);

        Task task = updateTaskStatusUseCase.updateStatus(id, request.status(), currentUserId, isAdmin);
        return ResponseEntity.ok(mapper.toResponse(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> list(Authentication authentication) {
        Long currentUserId = extractUserId(authentication);
        boolean isAdmin = isAdmin(authentication);

        List<TaskResponse> tasks = listTasksUseCase.listByUser(currentUserId, isAdmin).stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    private Long extractUserId(Authentication authentication) {
        return ((UserDetailsAdapter) authentication.getPrincipal()).getUserId();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}