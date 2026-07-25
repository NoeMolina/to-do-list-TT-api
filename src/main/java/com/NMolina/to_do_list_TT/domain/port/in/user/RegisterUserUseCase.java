package com.NMolina.to_do_list_TT.domain.port.in.user;

import com.NMolina.to_do_list_TT.domain.model.User;

public interface RegisterUserUseCase {

    User register(RegisterUserCommand command);

    record RegisterUserCommand(String username, String rawPassword) {
    }
}