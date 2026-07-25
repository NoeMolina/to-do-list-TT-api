package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Subtask;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.EstatusEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.SubtareaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.TareaEntity;

@Component
public class SubtareaPersistenceMapper {

    private final EstatusPersistenceMapper estatusMapper;

    public SubtareaPersistenceMapper(EstatusPersistenceMapper estatusMapper) {
        this.estatusMapper = estatusMapper;
    }

    public Subtask toDomain(SubtareaEntity entity) {
        if (entity == null)
            return null;
        return new Subtask(
                entity.getId(),
                entity.getTarea().getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                estatusMapper.toDomain(entity.getEstatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy());
    }

    public SubtareaEntity toEntity(Subtask domain, TareaEntity tarea, EstatusEntity estatus) {
        if (domain == null)
            return null;
        return new SubtareaEntity(
                domain.getId(),
                tarea,
                domain.getTitle(),
                domain.getDescription(),
                estatus);
    }
}