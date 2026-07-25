package com.NMolina.to_do_list_TT.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NMolina.to_do_list_TT.domain.exception.TaskNotFoundException;
import com.NMolina.to_do_list_TT.domain.model.Category;
import com.NMolina.to_do_list_TT.domain.model.Status;
import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.domain.port.in.task.CreateTaskUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.task.ListTasksUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.task.UpdateTaskStatusUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.CategoryRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.StatusRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.TaskRepositoryPort;

@Service
@Transactional
public class TaskService implements CreateTaskUseCase, UpdateTaskStatusUseCase, ListTasksUseCase {

    private static final String DEFAULT_INITIAL_STATUS = "PENDIENTE";

    private final TaskRepositoryPort taskRepository;
    private final CategoryRepositoryPort categoryRepository;
    private final StatusRepositoryPort statusRepository;

    public TaskService(TaskRepositoryPort taskRepository,
            CategoryRepositoryPort categoryRepository,
            StatusRepositoryPort statusRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
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

        task.changeStatus(newStatus);
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> listByUser(Long userId, boolean isAdmin) {
        return isAdmin ? taskRepository.findAll() : taskRepository.findAllByUserId(userId);
    }

    private void assertOwnership(Task task, Long userId) {
        if (!task.getUserId().equals(userId)) {
            throw new TaskNotFoundException(task.getId());
        }
    }
}