package com.java.course.auto_catalog.service;

import com.java.course.auto_catalog.dto.CategoryDTO;
import com.java.course.auto_catalog.dto.CategoryUpdateDTO;
import com.java.course.auto_catalog.dto.CategoryWithoutIdDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO save(Long carId, CategoryWithoutIdDTO categoryWithoutIdDTO);

    void deleteById(Long carId, Long categoryId);

    CategoryDTO updateCategory(Long carId, Long categoryId, CategoryUpdateDTO categoryUpdateDTO);

    CategoryDTO findById(Long carId, Long categoryId);

    List<CategoryDTO> getAllCategoryByCars(Long carId);
}
