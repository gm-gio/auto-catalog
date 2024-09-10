package com.java.course.auto_catalog.service.impl;

import com.java.course.auto_catalog.dto.ManufacturerDTO;
import com.java.course.auto_catalog.model.Manufacturer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;


    public <D, E> D convertToDto(E entity, Class<D> DTOClass) {
        return modelMapper.map(entity, DTOClass);
    }

    public <D, E> E convertToEntity(D DTO, Class<E> entityClass) {
        return modelMapper.map(DTO, entityClass);
    }

}
