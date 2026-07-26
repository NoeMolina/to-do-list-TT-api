package com.NMolina.to_do_list_TT.domain.port.in.category;

import java.util.List;

import com.NMolina.to_do_list_TT.domain.model.Category;

public interface ListCategoriesUseCase {

    List<Category> listAll();
}
