package com.NMolina.to_do_list_TT.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NMolina.to_do_list_TT.domain.exception.DuplicateUsernameException;
import com.NMolina.to_do_list_TT.domain.model.Role;
import com.NMolina.to_do_list_TT.domain.model.User;
import com.NMolina.to_do_list_TT.domain.port.in.user.RegisterUserUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.PasswordEncoderPort;
import com.NMolina.to_do_list_TT.domain.port.out.RoleRepositoryPort;
import com.NMolina.to_do_list_TT.domain.port.out.UserRepositoryPort;

@Service
@Transactional
public class UserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PasswordEncoderPort passwordEncoder;

    public UserService(UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository,
            PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterUserCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            throw new DuplicateUsernameException(command.username());
        }

        Role defaultRole = roleRepository.findByCode(Role.USER)
                .orElseThrow(() -> new IllegalStateException("Rol USER no encontrado en el catálogo"));

        String hashedPassword = passwordEncoder.encode(command.rawPassword());
        User user = User.register(command.username(), hashedPassword, defaultRole);
        return userRepository.save(user);
    }
}