package com.java.course.auto_catalog.service.impl;

import com.java.course.auto_catalog.dao.ManufacturerRepository;
import com.java.course.auto_catalog.dao.ModelRepository;
import com.java.course.auto_catalog.dto.ModelDTO;
import com.java.course.auto_catalog.dto.ModelUpdateDTO;
import com.java.course.auto_catalog.dto.ModelWithoutIdDTO;
import com.java.course.auto_catalog.exception.NotFoundException;
import com.java.course.auto_catalog.model.Manufacturer;
import com.java.course.auto_catalog.model.Model;
import com.java.course.auto_catalog.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final MapperService mapperService;
    private final ManufacturerRepository manufacturerRepository;

    @Override
    @Transactional
    public ModelDTO save(Long manufacturerId, ModelWithoutIdDTO modelWithoutIdDTO) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found"));


        Model model = mapperService.convertToEntity(modelWithoutIdDTO, Model.class);
        model.setManufacturer(manufacturer);

        Model savedModel = modelRepository.save(model);

        return mapperService.convertToDto(savedModel, ModelDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(Long manufacturerId, Long modelId) {

        modelRepository.deleteByManufacturerManufacturerIdAndModelId(manufacturerId, modelId);
    }


    @Override
    @Transactional
    public ModelDTO update(Long manufacturerId, Long modelId, ModelUpdateDTO modelUpdateDTO) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found"));

        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new NotFoundException("Model not found"));

        model.setModelName(modelUpdateDTO.getModelName());
        model.setModelCategory(modelUpdateDTO.getModelCategory());
        model.setManufacturer(manufacturer);

        Model updatedModel = modelRepository.save(model);

        return mapperService.convertToDto(updatedModel, ModelDTO.class);
    }

    @Override
    public ModelDTO findById(Long manufacturerId, Long modelId) {

        Model model = modelRepository.findByManufacturerManufacturerIdAndModelId(manufacturerId, modelId)
                .orElseThrow(() -> new NotFoundException("Model not Found"));

        return mapperService.convertToDto(model, ModelDTO.class);
    }

    @Override
    public List<ModelDTO> getAllModelsByManufacturer(Long manufacturerId) {

        List<Model> models = modelRepository.findByManufacturerManufacturerId(manufacturerId);
        return models.stream().map(model -> mapperService.convertToDto(model, ModelDTO.class)).collect(Collectors.toList());
    }
}
