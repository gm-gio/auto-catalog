package com.java.course.auto_catalog.service;

import com.java.course.auto_catalog.dto.CarDTO;
import com.java.course.auto_catalog.dto.CarSearchDTO;
import com.java.course.auto_catalog.dto.CarUpdateDTO;
import com.java.course.auto_catalog.dto.CarWithoutIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface CarService {

    CarDTO save(Long manufacturerId, Long modelId, CarWithoutIdDTO carWithoutIdDTO);


    void deleteById(Long manufacturerId, Long modelId, Long carId);


    CarDTO updateCar(Long manufacturerId, Long modelId, Long carId, CarUpdateDTO carUpdateDTO);


    CarDTO findById(Long manufacturerId, Long modelId, Long carId);


    Page<CarSearchDTO> getAllCars(CarSearchDTO searchDTO, Pageable pageable);
}
