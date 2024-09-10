package com.java.course.auto_catalog.service.impl;

import com.java.course.auto_catalog.dao.ManufacturerRepository;
import com.java.course.auto_catalog.dto.ManufacturerDTO;
import com.java.course.auto_catalog.dto.ManufacturerWithoutIdDTO;
import com.java.course.auto_catalog.exception.BadRequestException;
import com.java.course.auto_catalog.exception.NotFoundException;
import com.java.course.auto_catalog.model.Manufacturer;
import com.java.course.auto_catalog.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final MapperService mapperService;

    @Override
    @Transactional
    public ManufacturerDTO save(ManufacturerWithoutIdDTO manufacturerWithoutIdDTO) {

        if (manufacturerRepository.existsByManufacturerName(manufacturerWithoutIdDTO.getManufacturerName())) {
            throw new BadRequestException("Manufacturer with name " + manufacturerWithoutIdDTO.getManufacturerName() + " already exists.");
        }

        Manufacturer manufacturer = mapperService.convertToEntity(manufacturerWithoutIdDTO, Manufacturer.class);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return mapperService.convertToDto(savedManufacturer, ManufacturerDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(Long manufacturerId) {

        if (!manufacturerRepository.existsById(manufacturerId)) {
            throw new NotFoundException("Manufacturer not found");
        }
        manufacturerRepository.deleteById(manufacturerId);
    }

    @Override
    @Transactional
    public ManufacturerDTO updateManufacturer(Long manufacturerId, ManufacturerDTO manufacturerDTO) {

        Manufacturer existingManufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found"));


        existingManufacturer.setManufacturerName(manufacturerDTO.getManufacturerName());
        Manufacturer updatedManufacturer = manufacturerRepository.save(existingManufacturer);

        return mapperService.convertToDto(updatedManufacturer, ManufacturerDTO.class);
    }

    @Override
    public ManufacturerDTO getManufacturerById(Long manufacturerId) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found"));
        return mapperService.convertToDto(manufacturer, ManufacturerDTO.class);
    }

    @Override
    public List<ManufacturerDTO> getAllManufacturers() {

        return manufacturerRepository.findAll().stream()
                .map(manufacturer -> mapperService.convertToDto(manufacturer, ManufacturerDTO.class))
                .collect(Collectors.toList());
    }
}
