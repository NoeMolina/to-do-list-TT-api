package com.NMolina.to_do_list_TT.domain.port.in.user;

import com.NMolina.to_do_list_TT.domain.model.User;

import java.util.List;

public interface ListUsersUseCase {
    List<User> listAll();
}