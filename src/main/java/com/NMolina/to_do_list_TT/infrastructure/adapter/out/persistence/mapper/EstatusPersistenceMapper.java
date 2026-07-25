package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Status;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.EstatusEntity;

@Component
public class EstatusPersistenceMapper {

    public Status toDomain(EstatusEntity entity) {
        if (entity == null) return null;
        return new Status(entity.getId(), entity.getCodigo(), entity.getNombre());
    }

    public EstatusEntity toEntity(Status domain) {
        if (domain == null) return null;
        return new EstatusEntity(domain.getId(), domain.getCode(), domain.getName());
    }
}