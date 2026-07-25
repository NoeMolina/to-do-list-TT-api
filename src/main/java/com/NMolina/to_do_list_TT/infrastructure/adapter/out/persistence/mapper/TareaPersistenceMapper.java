package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Task;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.CategoriaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.EstatusEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.TareaEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

@Component
public class TareaPersistenceMapper {

    private final CategoriaPersistenceMapper categoriaMapper;
    private final EstatusPersistenceMapper estatusMapper;

    public TareaPersistenceMapper(CategoriaPersistenceMapper categoriaMapper,
            EstatusPersistenceMapper estatusMapper) {
        this.categoriaMapper = categoriaMapper;
        this.estatusMapper = estatusMapper;
    }

    public Task toDomain(TareaEntity entity) {
        if (entity == null)
            return null;
        return new Task(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getFechaVencimiento(),
                categoriaMapper.toDomain(entity.getCategoria()),
                estatusMapper.toDomain(entity.getEstatus()),
                entity.getUsuario().getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy());
    }

    public TareaEntity toEntity(Task domain, CategoriaEntity categoria,
            EstatusEntity estatus, UsuarioEntity usuario) {
        if (domain == null)
            return null;
        return new TareaEntity(
                domain.getId(),
                domain.getTitle(),
                domain.getDescription(),
                domain.getDueDate(),
                categoria,
                estatus,
                usuario);
    }
}