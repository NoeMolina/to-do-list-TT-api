package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Role;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.RoleEntity;

@Component
public class RolePersistenceMapper {

    public Role toDomain(RoleEntity entity) {
        if (entity == null)
            return null;
        return new Role(entity.getId(), entity.getCodigo(), entity.getNombre());
    }

    public RoleEntity toEntity(Role domain) {
        if (domain == null)
            return null;
        return new RoleEntity(domain.getId(), domain.getCode(), domain.getName());
    }
}