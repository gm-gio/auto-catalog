package com.java.course.auto_catalog.controller;

import com.java.course.auto_catalog.dto.CarDTO;
import com.java.course.auto_catalog.dto.CarSearchDTO;
import com.java.course.auto_catalog.dto.CarUpdateDTO;
import com.java.course.auto_catalog.dto.CarWithoutIdDTO;
import com.java.course.auto_catalog.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturers/{manufacturerId}/models/{modelId}/cars")
public class CarController {

    private final CarService carService;

    @Operation(
            summary = "Create a new Car",
            description = "Creates a new Car. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Create", description = "Create operations")
    @PostMapping("/new")
    public ResponseEntity<CarDTO> save(
            @PathVariable Long manufacturerId,
            @PathVariable Long modelId,
            @RequestBody CarWithoutIdDTO carWithoutIdDTO) {

        CarDTO savedCarDto = carService.save(manufacturerId, modelId, carWithoutIdDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarDto);
    }

    @Operation(
            summary = "Deletes Car by ID",
            description = "Deletes Car by ID. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Delete", description = "Delete By ID operations")
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long manufacturerId,
                                              @PathVariable Long modelId,
                                              @PathVariable Long carId) {
        carService.deleteById(manufacturerId, modelId, carId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update Car",
            description = "Update Car, Requires Beaver Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Update", description = "Update operations")
    @PutMapping("/{carId}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long carId,
                                            @PathVariable Long manufacturerId,
                                            @PathVariable Long modelId,
                                            @RequestBody CarUpdateDTO carUpdateDTO) {

        CarDTO updatedCar = carService.updateCar(manufacturerId, modelId, carId, carUpdateDTO);
        return ResponseEntity.ok(updatedCar);
    }
    @Operation(
            summary = "Gets  Car By ID",
            description = "Gets Car. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find", description = "Find By ID operations")
    @GetMapping("/{carId}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long manufacturerId,
                                             @PathVariable Long modelId,
                                             @PathVariable Long carId) {
        CarDTO carDTO = carService.findById(manufacturerId, modelId, carId);

        return ResponseEntity.ok(carDTO);
    }
    @Operation(
            summary = "Gets all Cars",
            description = "Gets all Cars. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find All", description = "Find All  operations")
    @GetMapping("/search")
    public Page<CarSearchDTO> searchCars(
            @RequestParam(required = false) Long manufacturerId,
            @RequestParam(required = false) Long modelId,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) Set<Long> categoryIds,
            @PageableDefault Pageable pageable) {

        CarSearchDTO searchDTO = new CarSearchDTO(manufacturerId, modelId, minYear, maxYear, categoryIds);
        return carService.getAllCars(searchDTO, pageable);
    }


}
