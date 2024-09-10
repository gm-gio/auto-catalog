package com.java.course.auto_catalog.dao;

import com.java.course.auto_catalog.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    Optional<Car> findByManufacturerManufacturerIdAndModelModelIdAndCarId(Long manufacturerId, Long modelId, Long carId);
    void deleteByManufacturerManufacturerIdAndModelModelIdAndCarId(Long manufacturerId, Long modelId, Long carId);
}

