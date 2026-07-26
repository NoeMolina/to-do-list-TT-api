package com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NMolina.to_do_list_TT.domain.port.in.category.ListCategoriesUseCase;
import com.NMolina.to_do_list_TT.infrastructure.adapter.in.web.dto.response.CategoryResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(ListCategoriesUseCase listCategoriesUseCase) {
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return listCategoriesUseCase.listAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getCode(), c.getName()))
                .toList();
    }
}
