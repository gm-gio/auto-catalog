package com.java.course.auto_catalog.service.impl;

import com.java.course.auto_catalog.dao.CarRepository;
import com.java.course.auto_catalog.dao.CarSpecification;
import com.java.course.auto_catalog.dao.ManufacturerRepository;
import com.java.course.auto_catalog.dao.ModelRepository;
import com.java.course.auto_catalog.dto.CarDTO;
import com.java.course.auto_catalog.dto.CarSearchDTO;
import com.java.course.auto_catalog.dto.CarUpdateDTO;
import com.java.course.auto_catalog.dto.CarWithoutIdDTO;
import com.java.course.auto_catalog.exception.NotFoundException;
import com.java.course.auto_catalog.model.Car;
import com.java.course.auto_catalog.model.Category;
import com.java.course.auto_catalog.model.Manufacturer;
import com.java.course.auto_catalog.model.Model;
import com.java.course.auto_catalog.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final MapperService mapperService;
    private final ManufacturerRepository manufacturerRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CarDTO save(Long manufacturerId, Long modelId, CarWithoutIdDTO carWithoutIdDTO) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found"));

        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new NotFoundException("Model not found"));

        Car car = mapperService.convertToEntity(carWithoutIdDTO, Car.class);
        car.setManufacturer(manufacturer);
        car.setModel(model);

        Car savedCar = carRepository.save(car);

        return mapperService.convertToDto(savedCar, CarDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(Long manufacturerId, Long modelId, Long carId) {
        carRepository.deleteByManufacturerManufacturerIdAndModelModelIdAndCarId(manufacturerId, modelId, carId);
    }

    @Override
    @Transactional
    public CarDTO updateCar(Long manufacturerId, Long modelId, Long carId, CarUpdateDTO carUpdateDTO) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found"));

        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new NotFoundException("Model not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found"));


        Set<Category> categories = carUpdateDTO.getCategories().stream()
                .map(categoryDTO -> modelMapper.map(categoryDTO, Category.class))
                .collect(Collectors.toSet());

        car.setCategories(categories);
        car.setYear(carUpdateDTO.getCarYear());
        car.setManufacturer(manufacturer);
        car.setModel(model);

        Car updatedCar = carRepository.save(car);

        return mapperService.convertToDto(updatedCar, CarDTO.class);
    }


    @Override
    public CarDTO findById(Long manufacturerId, Long modelId, Long carId) {

        Car car = carRepository.findByManufacturerManufacturerIdAndModelModelIdAndCarId(manufacturerId, modelId, carId)
                .orElseThrow(() -> new NotFoundException("Car not found"));


        return mapperService.convertToDto(car, CarDTO.class);
    }

    @Override
    public Page<CarSearchDTO> getAllCars(CarSearchDTO searchDTO, Pageable pageable) {

        Specification<Car> spec = Specification.where(null);

        if (searchDTO.getManufacturerId() != null) {
            spec = spec.and(CarSpecification.filterByManufacturer(searchDTO.getManufacturerId()));
        }

        if (searchDTO.getModelId() != null) {
            spec = spec.and(CarSpecification.filterByModel(searchDTO.getModelId()));
        }

        if (searchDTO.getMinYear() != null && searchDTO.getMaxYear() != null) {
            spec = spec.and(CarSpecification.filterByYearRange(searchDTO.getMinYear(), searchDTO.getMaxYear()));
        }

        if (searchDTO.getCategoryIds() != null && !searchDTO.getCategoryIds().isEmpty()) {
            spec = spec.and(CarSpecification.filterByCategories(searchDTO.getCategoryIds()));
        }

        Page<Car> cars = carRepository.findAll(spec, pageable);

        return cars.map(car -> mapperService.convertToDto(car, CarSearchDTO.class));
    }

}
