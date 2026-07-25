package com.NMolina.to_do_list_TT.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NMolina.to_do_list_TT.domain.exception.SubtaskNotFoundException;
import com.NMolina.to_do_list_TT.domain.exception.TaskNotFoundException;
import com.NMolina.to_do_list_TT.domain.model.Status;
import com.NMolina.to_do_list_TT.domain.model.Subtask;
import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.domain.port.in.subtask.CreateSubtaskUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.subtask.UpdateSubtaskStatusUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.StatusRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.SubtaskRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.TaskRepositoryPort;

@Service
@Transactional
public class SubtaskService implements CreateSubtaskUseCase, UpdateSubtaskStatusUseCase {

    private static final String DEFAULT_INITIAL_STATUS = "PENDIENTE";

    private final SubtaskRepositoryPort subtaskRepository;
    private final TaskRepositoryPort taskRepository;
    private final StatusRepositoryPort statusRepository;

    public SubtaskService(SubtaskRepositoryPort subtaskRepository,
            TaskRepositoryPort taskRepository,
            StatusRepositoryPort statusRepository) {
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Subtask createSubtask(CreateSubtaskCommand command) {
        taskRepository.findById(command.taskId())
                .orElseThrow(() -> new TaskNotFoundException(command.taskId()));

        Status initialStatus = statusRepository.findByCode(DEFAULT_INITIAL_STATUS)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el estatus inicial '%s' en el catálogo".formatted(DEFAULT_INITIAL_STATUS)));

        Subtask subtask = Subtask.create(
                command.taskId(),
                command.title(),
                command.description(),
                initialStatus);

        return subtaskRepository.save(subtask);
    }

    @Override
    public Subtask updateStatus(Long subtaskId, String newStatusCode, Long userId) {
        Subtask subtask = subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new SubtaskNotFoundException(subtaskId));

        Task parentTask = taskRepository.findById(subtask.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException(subtask.getTaskId()));

        if (!parentTask.getUserId().equals(userId)) {
            throw new SubtaskNotFoundException(subtaskId);
        }

        Status newStatus = statusRepository.findByCode(newStatusCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El estatus '%s' no existe en el catálogo".formatted(newStatusCode)));

        subtask.changeStatus(newStatus);
        return subtaskRepository.save(subtask);
    }
}