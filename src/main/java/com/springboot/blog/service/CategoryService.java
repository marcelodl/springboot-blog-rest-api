package com.springboot.blog.service;

import com.springboot.blog.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

     CategoryDTO createCategory(CategoryDTO categoryDTO);

     CategoryDTO getCategory(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);


}
