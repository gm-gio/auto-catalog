package com.java.course.auto_catalog.config;

import com.java.course.auto_catalog.dto.CarWithoutIdDTO;
import com.java.course.auto_catalog.dto.CategoryWithoutIdDTO;
import com.java.course.auto_catalog.dto.ManufacturerWithoutIdDTO;
import com.java.course.auto_catalog.dto.ModelWithoutIdDTO;
import com.java.course.auto_catalog.model.Car;
import com.java.course.auto_catalog.model.Category;
import com.java.course.auto_catalog.model.Manufacturer;
import com.java.course.auto_catalog.model.Model;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(ManufacturerWithoutIdDTO.class, Manufacturer.class)
                .addMappings(mapper -> mapper.skip(Manufacturer::setManufacturerId));

        modelMapper.typeMap(CarWithoutIdDTO.class, Car.class)
                .addMappings(mapper -> mapper.skip(Car::setCarId));

        modelMapper.typeMap(ModelWithoutIdDTO.class, Model.class)
                .addMappings(mapper -> mapper.skip(Model::setModelId));

        modelMapper.typeMap(CategoryWithoutIdDTO.class, Category.class)
                .addMappings(mapper -> mapper.skip(Category::setCategoryId));

        return modelMapper;
    }

}

