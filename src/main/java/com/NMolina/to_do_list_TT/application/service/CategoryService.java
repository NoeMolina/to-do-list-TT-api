package com.NMolina.to_do_list_TT.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NMolina.to_do_list_TT.domain.model.Category;
import com.NMolina.to_do_list_TT.domain.port.in.category.ListCategoriesUseCase;
import com.NMolina.to_do_list_TT.domain.port.out.CategoryRepositoryPort;

@Service
@Transactional(readOnly = true)
public class CategoryService implements ListCategoriesUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public CategoryService(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }
}