package com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NMolina.to_do_list_TT.domain.model.Status;
import com.NMolina.to_do_list_TT.domain.port.out.StatusRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.mapper.EstatusPersistenceMapper;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.repository.EstatusJpaRepository;

@Component
public class EstatusRepositoryAdapter implements StatusRepositoryPort {

    private final EstatusJpaRepository jpaRepository;
    private final EstatusPersistenceMapper mapper;

    public EstatusRepositoryAdapter(EstatusJpaRepository jpaRepository, EstatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Status> findByCode(String code) {
        return jpaRepository.findByCodigo(code).map(mapper::toDomain);
    }

    @Override
    public Optional<Status> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
