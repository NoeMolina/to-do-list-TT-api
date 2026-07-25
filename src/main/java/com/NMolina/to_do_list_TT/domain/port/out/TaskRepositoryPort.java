package com.NMolina.to_do_list_TT.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.NMolina.to_do_list_TT.domain.model.Task;

public interface TaskRepositoryPort {
    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    List<Task> findAll();

    void deleteById(Long id);
}