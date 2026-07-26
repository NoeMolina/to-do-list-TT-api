package com.NMolina.to_do_list_TT.application.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NMolina.to_do_list_TT.domain.exception.PendingSubtasksException;
import com.NMolina.to_do_list_TT.domain.exception.TaskNotFoundException;
import com.NMolina.to_do_list_TT.domain.model.Category;
import com.NMolina.to_do_list_TT.domain.model.Status;
import com.NMolina.to_do_list_TT.domain.model.Subtask;
import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.domain.port.in.task.CreateTaskUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.task.ListTasksUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.task.UpdateTaskStatusUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.CategoryRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.StatusRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.SubtaskRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.TaskRepositoryPort;

@Service
@Transactional
public class TaskService implements CreateTaskUseCase, UpdateTaskStatusUseCase, ListTasksUseCase {

    private static final String DEFAULT_INITIAL_STATUS = "PENDIENTE";
    private static final String STATUS_COMPLETADO = "COMPLETADO";
    private static final String STATUS_CANCELADO = "CANCELADO";
    private static final Set<String> ESTATUS_FINALES = Set.of(STATUS_COMPLETADO, STATUS_CANCELADO);

    private final TaskRepositoryPort taskRepository;
    private final CategoryRepositoryPort categoryRepository;
    private final StatusRepositoryPort statusRepository;
    private final SubtaskRepositoryPort subtaskRepository;

    public TaskService(TaskRepositoryPort taskRepository,
            CategoryRepositoryPort categoryRepository,
            StatusRepositoryPort statusRepository, SubtaskRepositoryPort subtaskRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
        this.subtaskRepository = subtaskRepository;
    }

    @Override
    public Task createTask(CreateTaskCommand command) {
        Category category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría indicada no existe"));

        Status initialStatus = statusRepository.findByCode(DEFAULT_INITIAL_STATUS)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el estatus inicial '%s' en el catálogo".formatted(DEFAULT_INITIAL_STATUS)));

        Task task = Task.create(
                command.title(),
                command.description(),
                command.dueDate(),
                category,
                initialStatus,
                command.userId());

        return taskRepository.save(task);
    }

    @Override
    public Task updateStatus(Long taskId, String newStatusCode, Long userId, boolean isAdmin) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!isAdmin) {
            assertOwnership(task, userId);
        }

        Status newStatus = statusRepository.findByCode(newStatusCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El estatus '%s' no existe en el catálogo".formatted(newStatusCode)));

        List<Subtask> subtasks = subtaskRepository.findAllByTaskId(taskId);

        if (STATUS_COMPLETADO.equals(newStatus.getCode())) {
            validateSubtasksReadyToComplete(taskId, subtasks);
        }

        task.changeStatus(newStatus);
        Task saved = taskRepository.save(task);

        if (STATUS_CANCELADO.equals(newStatus.getCode())) {
            cascadeCancelSubtasks(subtasks);
        }

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> listByUser(Long userId, boolean isAdmin) {
        return isAdmin ? taskRepository.findAll() : taskRepository.findAllByUserId(userId);
    }

    private void validateSubtasksReadyToComplete(Long taskId, List<Subtask> subtasks) {
        boolean hayPendientes = subtasks.stream()
                .anyMatch(subtask -> !ESTATUS_FINALES.contains(subtask.getStatus().getCode()));

        if (hayPendientes) {
            throw new PendingSubtasksException(taskId);
        }
    }

    private void cascadeCancelSubtasks(List<Subtask> subtasks) {
        Status cancelado = statusRepository.findByCode(STATUS_CANCELADO)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el estatus '%s' en el catálogo".formatted(STATUS_CANCELADO)));

        List<Subtask> subtasksToCancel = subtasks.stream()
                .filter(subtask -> !ESTATUS_FINALES.contains(subtask.getStatus().getCode()))
                .peek(subtask -> subtask.changeStatus(cancelado))
                .toList();

        if (!subtasksToCancel.isEmpty()) {
            subtaskRepository.saveAll(subtasksToCancel);
        }
    }

    private void assertOwnership(Task task, Long userId) {
        if (!task.getUserId().equals(userId)) {
            throw new TaskNotFoundException(task.getId());
        }
    }
}