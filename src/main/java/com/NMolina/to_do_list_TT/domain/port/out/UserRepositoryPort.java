package com.NMolina.to_do_list_TT.domain.port.out;

import java.util.Optional;

import com.NMolina.to_do_list_TT.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}