package com.NMolina.to_do_list_TT.application.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.NMolina.to_do_list_TT.domain.exception.UserNotFoundException;
import com.NMolina.to_do_list_TT.domain.model.User;
import com.NMolina.to_do_list_TT.domain.port.in.user.AuthenticateUserUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.PasswordEncoderPort;
import com.NMolina.to_do_list_TT.domain.port.out.UserRepositoryPort;
import com.NMolina.to_do_list_TT.infrastructure.adapter.out.security.JwtService;

@Service
public class AuthService implements AuthenticateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResult authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole().getCode());
        return new AuthResult(user.getId(), user.getUsername(), token);
    }
}