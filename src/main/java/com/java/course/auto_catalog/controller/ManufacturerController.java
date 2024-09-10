package com.java.course.auto_catalog.controller;

import com.java.course.auto_catalog.dto.ManufacturerDTO;
import com.java.course.auto_catalog.dto.ManufacturerWithoutIdDTO;
import com.java.course.auto_catalog.service.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @Operation(
            summary = "Creates a new manufacturer",
            description = "Creates a new manufacturer. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Create", description = "Create operations")
    @PostMapping("/new")
    public ResponseEntity<ManufacturerDTO> saveManufacturer(@RequestBody ManufacturerWithoutIdDTO manufacturerWithoutIdDTO) {

        ManufacturerDTO savedManufacturerDTO = manufacturerService.save(manufacturerWithoutIdDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManufacturerDTO);
    }
    @Operation(
            summary = "Deletes a manufacturer by ID",
            description = "Deletes a manufacturer by ID. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Delete", description = "Delete By Id operations")
    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<Void> deleteManufacturerById(@PathVariable Long manufacturerId) {
        manufacturerService.deleteById(manufacturerId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Updates a manufacturer",
            description = "Updates a manufacturer. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Update", description = "Update operations")
    @PutMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@PathVariable Long manufacturerId,
                                                              @RequestBody ManufacturerDTO manufacturerDTO) {
        ManufacturerDTO updatedManufacturerDTO = manufacturerService.updateManufacturer(manufacturerId, manufacturerDTO);
        return ResponseEntity.ok(updatedManufacturerDTO);
    }
    @Operation(
            summary = "Gets  manufacturer By ID",
            description = "Gets manufacturer. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find", description = "Find By ID operations")
    @GetMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerDTO> getManufacturerById(@PathVariable Long manufacturerId) {
        ManufacturerDTO manufacturerDTO = manufacturerService.getManufacturerById(manufacturerId);
        return ResponseEntity.ok(manufacturerDTO);
    }
    @Operation(
            summary = "Gets all manufacturers",
            description = "Gets all manufacturers. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find All", description = "Find All  operations")
    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers() {
        List<ManufacturerDTO> manufacturers = manufacturerService.getAllManufacturers();
        return ResponseEntity.ok(manufacturers);
    }
}
