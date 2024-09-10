package com.java.course.auto_catalog.dao;

import com.java.course.auto_catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByCarsCarIdAndCategoryId(Long carId, Long categoryId);
    Optional<Category> findByCarsCarIdAndCategoryId(Long carId, Long categoryId);
    List<Category> findByCarsCarId(Long carId);
}
