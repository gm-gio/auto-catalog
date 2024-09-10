package com.java.course.auto_catalog.dao;

import com.java.course.auto_catalog.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    boolean existsByManufacturerName(String name);
}
