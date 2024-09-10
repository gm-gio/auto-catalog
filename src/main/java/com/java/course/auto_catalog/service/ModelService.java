package com.java.course.auto_catalog.service;

import com.java.course.auto_catalog.dto.ModelDTO;
import com.java.course.auto_catalog.dto.ModelUpdateDTO;
import com.java.course.auto_catalog.dto.ModelWithoutIdDTO;
import com.java.course.auto_catalog.model.Model;

import java.util.List;

public interface ModelService {

    ModelDTO save(Long manufacturerId, ModelWithoutIdDTO modelWithoutIdDTO);

    void deleteById(Long manufacturerId, Long modelId);

    ModelDTO update(Long manufacturerId, Long modelId, ModelUpdateDTO modelUpdateDTO);

    ModelDTO findById(Long manufacturerId, Long modelId);

    List<ModelDTO> getAllModelsByManufacturer(Long manufacturerId);
}
