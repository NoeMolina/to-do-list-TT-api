package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.EstatusEntity;

public interface EstatusJpaRepository extends JpaRepository<EstatusEntity, Integer> {
    Optional<EstatusEntity> findByCodigo(String codigo);
}