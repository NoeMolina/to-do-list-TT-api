package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Role;
import com.NMolina.to_do_list_TT.domain.port.out.RoleRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.RolePersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.RoleJpaRepository;

@Component
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository jpaRepository;
    private final RolePersistenceMapper mapper;

    public RoleRepositoryAdapter(RoleJpaRepository jpaRepository, RolePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Role> findByCode(String code) {
        return jpaRepository.findByCodigo(code).map(mapper::toDomain);
    }
}