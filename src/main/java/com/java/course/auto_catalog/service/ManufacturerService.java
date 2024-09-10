package com.java.course.auto_catalog.service;

import com.java.course.auto_catalog.dto.ManufacturerDTO;
import com.java.course.auto_catalog.dto.ManufacturerWithoutIdDTO;
import com.java.course.auto_catalog.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    ManufacturerDTO save(ManufacturerWithoutIdDTO manufacturerWithoutIdDTO);

    void deleteById(Long manufacturerId);

    ManufacturerDTO updateManufacturer(Long manufacturerId, ManufacturerDTO manufacturerDTO);

    ManufacturerDTO getManufacturerById(Long manufacturerId);

    List<ManufacturerDTO> getAllManufacturers();
}
