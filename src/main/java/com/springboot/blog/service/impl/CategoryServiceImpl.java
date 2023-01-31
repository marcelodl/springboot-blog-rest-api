package com.springboot.blog.service.impl;

import com.springboot.blog.config.security.exception.ResourceNotFoundException;
import com.springboot.blog.dto.CategoryDTO;
import com.springboot.blog.model.Category;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = mapToEntity(categoryDTO);
        category = categoryRepository.save(category);
        return mapToDTO(category);
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id",id.toString()));
        return mapToDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","id",id.toString()));

        if(StringUtils.hasText(categoryDTO.getName())) {
            category.setName(categoryDTO.getName());
        }

        if(StringUtils.hasText(categoryDTO.getDescription())) {
            category.setDescription(categoryDTO.getDescription());
        }

        Category updatedCategory = categoryRepository.save(category);

        return mapToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","id", id.toString()));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categoryDTOList = categoryRepository.findAll().stream().map(c -> mapToDTO(c)).collect(Collectors.toList());
        return categoryDTOList;
    }

    private Category mapToEntity(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        return category;
    }

    private CategoryDTO mapToDTO(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }
}
