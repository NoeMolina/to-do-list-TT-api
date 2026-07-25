package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper;

import com.NMolina.to_do_list_TT.domain.model.Category;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaPersistenceMapper {

    public Category toDomain(CategoriaEntity entity) {
        if (entity == null)
            return null;
        return new Category(entity.getId(), entity.getCodigo(), entity.getNombre());
    }

    public CategoriaEntity toEntity(Category domain) {
        if (domain == null)
            return null;
        return new CategoriaEntity(domain.getId(), domain.getCode(), domain.getName());
    }
}