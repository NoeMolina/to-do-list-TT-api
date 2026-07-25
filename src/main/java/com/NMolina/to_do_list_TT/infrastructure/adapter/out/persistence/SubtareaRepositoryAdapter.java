package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.List;
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
