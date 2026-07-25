package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NMolina.to_do_list_TT.domain.port.in.user.AuthenticateUserUseCase;
import com.NMolina.to_do_list_TT.domain.port.in.user.RegisterUserUseCase;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request.LoginRequest;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.request.RegisterRequest;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.AuthResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase,
            AuthenticateUserUseCase authenticateUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        registerUserUseCase.register(
                new RegisterUserUseCase.RegisterUserCommand(request.username(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        var result = authenticateUserUseCase.authenticate(request.username(), request.password());
        return ResponseEntity.ok(new AuthResponse(result.userId(), result.username(), result.token()));
    }
}