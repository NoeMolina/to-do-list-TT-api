package com.NMolina.to_do_list_TT.domain.port.out;

import java.util.Optional;

import com.NMolina.to_do_list_TT.domain.model.Role;

public interface RoleRepositoryPort {
    Optional<Role> findByCode(String code);
}
