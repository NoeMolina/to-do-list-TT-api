package com.NMolina.to_do_list_TT.application.service;

import com.NMolina.to_do_list_TT.domain.model.Status;
import com.NMolina.to_do_list_TT.domain.port.in.status.ListStatusesUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.StatusRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatusService implements ListStatusesUseCase {

    private final StatusRepositoryPort statusRepository;

    public StatusService(StatusRepositoryPort statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> listAll() {
        return statusRepository.findAll();
    }
}