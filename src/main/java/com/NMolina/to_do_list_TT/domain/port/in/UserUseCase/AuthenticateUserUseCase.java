package com.NMolina.to_do_list_TT.domain.port.in.UserUseCase;

public interface AuthenticateUserUseCase {

    AuthResult authenticate(String username, String rawPassword);

    record AuthResult(Long userId, String username, String token) {
    }
}