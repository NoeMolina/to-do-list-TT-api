package com.NMolina.to_do_list_TT.domain.port.in.UserUseCase;

import com.NMolina.to_do_list_TT.domain.model.User;

public interface RegisterUserUseCase {

    User register(RegisterUserCommand command);

    record RegisterUserCommand(String username, String rawPassword) {
    }
}