package com.java.course.auto_catalog.service.impl;

import com.java.course.auto_catalog.dao.CarRepository;
import com.java.course.auto_catalog.dao.CategoryRepository;
import com.java.course.auto_catalog.dto.CategoryDTO;
import com.java.course.auto_catalog.dto.CategoryUpdateDTO;
import com.java.course.auto_catalog.dto.CategoryWithoutIdDTO;
import com.java.course.auto_catalog.exception.NotFoundException;
import com.java.course.auto_catalog.model.Car;
import com.java.course.auto_catalog.model.Category;
import com.java.course.auto_catalog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperService mapperService;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public CategoryDTO save(Long carId, CategoryWithoutIdDTO categoryWithoutIdDTO) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found"));

        Category category = mapperService.convertToEntity(categoryWithoutIdDTO, Category.class);
        category.getCars().add(car);

        Category savedCategory = categoryRepository.save(category);

        return mapperService.convertToDto(savedCategory, CategoryDTO.class);

    }

    @Override
    @Transactional
    public void deleteById(Long carId, Long categoryId) {

        categoryRepository.deleteByCarsCarIdAndCategoryId(carId, categoryId);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long carId, Long categoryId, CategoryUpdateDTO categoryUpdateDTO) {

        Category category = categoryRepository.findByCarsCarIdAndCategoryId(carId, categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        category.setCategoryName(categoryUpdateDTO.getCategoryName());

        Set<Car> cars = new HashSet<>(carRepository.findAllById(categoryUpdateDTO.getCarIds()));
        category.setCars(cars);


        Category updatedCategory = categoryRepository.save(category);

        return mapperService.convertToDto(updatedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findById(Long carId, Long categoryId) {

        Category category = categoryRepository.findByCarsCarIdAndCategoryId(carId, categoryId)
                .orElseThrow(() -> new NotFoundException("Category not Found"));

        return mapperService.convertToDto(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategoryByCars(Long carId) {

        List<Category> categories = categoryRepository.findByCarsCarId(carId);
        return categories.stream().map(category -> mapperService.convertToDto(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

}
