package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.CategoriaEntity;

public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Integer> {
}