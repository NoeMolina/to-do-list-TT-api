package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.controller;

import com.NMolina.to_do_list_TT.domain.port.in.status.ListStatusesUseCase;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.StatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    private final ListStatusesUseCase listStatusesUseCase;

    public StatusController(ListStatusesUseCase listStatusesUseCase) {
        this.listStatusesUseCase = listStatusesUseCase;
    }

    @GetMapping
    public List<StatusResponse> list() {
        return listStatusesUseCase.listAll().stream()
            .map(s -> new StatusResponse(s.getId(), s.getCode(), s.getName()))
            .toList();
    }
}