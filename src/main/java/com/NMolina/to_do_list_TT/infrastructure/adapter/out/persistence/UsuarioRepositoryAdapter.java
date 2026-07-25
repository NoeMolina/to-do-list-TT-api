package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.User;
import com.NMolina.to_do_list_TT.domain.port.out.UserRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.RoleEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.RoleJpaRepository;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

@Component
public class UsuarioRepositoryAdapter implements UserRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UsuarioPersistenceMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, RoleJpaRepository roleJpaRepository,
            UsuarioPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        RoleEntity rol = roleJpaRepository.findByCodigo(user.getRole().getCode())
                .orElseThrow(() -> new IllegalStateException("Rol no encontrado"));
        UsuarioEntity entity = mapper.toEntity(user, rol);
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