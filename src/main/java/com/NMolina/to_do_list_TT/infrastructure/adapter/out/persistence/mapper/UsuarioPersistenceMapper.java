package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.User;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

@Component
public class UsuarioPersistenceMapper {

    public User toDomain(UsuarioEntity entity) {
        if (entity == null)
            return null;
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.isActivo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy());
    }

    public UsuarioEntity toEntity(User domain) {
        if (domain == null)
            return null;
        return new UsuarioEntity(
                domain.getId(),
                domain.getUsername(),
                domain.getPasswordHash(),
                domain.isActive());
    }
}