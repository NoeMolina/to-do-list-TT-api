package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Subtask;
import com.NMolina.to_do_list_TT.domain.port.out.SubtaskRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.EstatusEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.SubtareaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.TareaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.SubtareaPersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.EstatusJpaRepository;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.SubtareaJpaRepository;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.TareaJpaRepository;

@Component
public class SubtareaRepositoryAdapter implements SubtaskRepositoryPort {
    private final SubtareaJpaRepository subtareaJpaRepository;
    private final TareaJpaRepository tareaJpaRepository;
    private final EstatusJpaRepository estatusJpaRepository;
    private final SubtareaPersistenceMapper mapper;

    public SubtareaRepositoryAdapter(SubtareaJpaRepository subtareaJpaRepository,
            TareaJpaRepository tareaJpaRepository,
            EstatusJpaRepository estatusJpaRepository,
            SubtareaPersistenceMapper mapper) {
        this.subtareaJpaRepository = subtareaJpaRepository;
        this.tareaJpaRepository = tareaJpaRepository;
        this.estatusJpaRepository = estatusJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Subtask save(Subtask subtask) {
        TareaEntity tarea = tareaJpaRepository.findById(subtask.getTaskId())
                .orElseThrow(() -> new IllegalStateException("Tarea no encontrada"));
        EstatusEntity estatus = estatusJpaRepository.findById(subtask.getStatus().getId())
                .orElseThrow(() -> new IllegalStateException("Estatus no encontrado"));

        SubtareaEntity entity = mapper.toEntity(subtask, tarea, estatus);
        SubtareaEntity saved = subtareaJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Subtask> saveAll(List<Subtask> subtasks) {
        if (subtasks.isEmpty())
            return List.of();

        Map<Long, TareaEntity> tareaCache = new HashMap<>();
        Map<Integer, EstatusEntity> estatusCache = new HashMap<>();

        List<SubtareaEntity> entities = subtasks.stream()
                .map(subtask -> {
                    TareaEntity tarea = tareaCache.computeIfAbsent(subtask.getTaskId(),
                            id -> tareaJpaRepository.findById(id)
                                    .orElseThrow(() -> new IllegalStateException("Tarea no encontrada")));
                    EstatusEntity estatus = estatusCache.computeIfAbsent(subtask.getStatus().getId(),
                            id -> estatusJpaRepository.findById(id)
                                    .orElseThrow(() -> new IllegalStateException("Estatus no encontrado")));
                    return mapper.toEntity(subtask, tarea, estatus);
                })
                .toList();

        return subtareaJpaRepository.saveAll(entities).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Subtask> findById(Long id) {
        return subtareaJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Subtask> findAllByTaskId(Long taskId) {
        return subtareaJpaRepository.findAllByTareaId(taskId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        subtareaJpaRepository.deleteById(id);
    }

}
