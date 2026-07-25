package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.User;
import com.NMolina.to_do_list_TT.domain.port.out.UserRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

@Component
public class UsuarioRepositoryAdapter implements UserRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioPersistenceMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, UsuarioPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UsuarioEntity entity = mapper.toEntity(user);
        UsuarioEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}