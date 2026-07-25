package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.TareaEntity;

public interface TareaJpaRepository extends JpaRepository<TareaEntity, Long> {
    List<TareaEntity> findAllByUsuarioId(Long usuarioId);
}