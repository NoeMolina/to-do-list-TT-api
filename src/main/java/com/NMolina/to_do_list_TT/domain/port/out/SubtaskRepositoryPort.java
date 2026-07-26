package com.NMolina.to_do_list_TT.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.NMolina.to_do_list_TT.domain.model.Subtask;

public interface SubtaskRepositoryPort {
    Subtask save(Subtask subtask);

    List<Subtask> saveAll(List<Subtask> subtasks);

    Optional<Subtask> findById(Long id);

    List<Subtask> findAllByTaskId(Long taskId);

    void deleteById(Long id);
}