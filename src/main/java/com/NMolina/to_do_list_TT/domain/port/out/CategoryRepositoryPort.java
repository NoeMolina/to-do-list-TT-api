package com.NMolina.to_do_list_TT.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.NMolina.to_do_list_TT.domain.model.Category;

public interface CategoryRepositoryPort {
    Optional<Category> findById(Integer id);

    List<Category> findAll();
}