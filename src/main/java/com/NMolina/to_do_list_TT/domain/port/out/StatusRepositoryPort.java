package com.NMolina.to_do_list_TT.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.NMolina.to_do_list_TT.domain.model.Status;

public interface StatusRepositoryPort {
    Optional<Status> findByCode(String code);

    Optional<Status> findById(Integer id);

    List<Status> findAll();
}