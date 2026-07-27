package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NMolina.to_do_list_TT.domain.port.in.user.ListUsersUseCase;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ListUsersUseCase listUsersUseCase;

    public UserController(ListUsersUseCase listUsersUseCase) {
        this.listUsersUseCase = listUsersUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> list() {
        return listUsersUseCase.listAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername()))
                .toList();
    }
}
