package com.java.course.auto_catalog.dao;

import com.java.course.auto_catalog.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByManufacturerManufacturerIdAndModelId(Long manufacturerId, Long modelId);

    void deleteByManufacturerManufacturerIdAndModelId(Long manufacturerId, Long modelId);

    List<Model> findByManufacturerManufacturerId(Long manufacturerId);
}
