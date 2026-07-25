package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}