package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Category;
import com.NMolina.to_do_list_TT.domain.port.out.CategoryRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.CategoriaPersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.CategoriaJpaRepository;

@Component
public class CategoriaRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoriaJpaRepository jpaRepository;
    private final CategoriaPersistenceMapper mapper;

    public CategoriaRepositoryAdapter(CategoriaJpaRepository jpaRepository, CategoriaPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}