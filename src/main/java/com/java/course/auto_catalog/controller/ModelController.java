package com.java.course.auto_catalog.controller;

import com.java.course.auto_catalog.dto.*;
import com.java.course.auto_catalog.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturers/{manufacturerId}/models")
public class ModelController {

    private final ModelService modelService;
    @Operation(
            summary = "Create a new model",
            description = "Creates a new model. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Create", description = "Create operations")
    @PostMapping("/new")
    public ResponseEntity<ModelDTO> save(@PathVariable Long manufacturerId, @RequestBody ModelWithoutIdDTO modelWithoutIdDTO) {

        ModelDTO savedModelDTO = modelService.save(manufacturerId, modelWithoutIdDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedModelDTO);
    }
    @Operation(
            summary = "Deletes a model by ID",
            description = "Deletes a model by ID. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Delete", description = "Delete By Id operations")
    @DeleteMapping("/{modelId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long manufacturerId, @PathVariable Long modelId) {

        modelService.deleteById(manufacturerId, modelId);

        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Updates model by ID",
            description = "Updates a model. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Update", description = "Update operations")
    @PutMapping("/{modelId}")
    public ResponseEntity<ModelDTO> updatedModel(@PathVariable Long manufacturerId,
                                                 @PathVariable Long modelId,
                                                 @RequestBody ModelUpdateDTO modelUpdateDTO) {

        ModelDTO updatedModel = modelService.update(manufacturerId, modelId, modelUpdateDTO);
        return ResponseEntity.ok(updatedModel);
    }
    @Operation(
            summary = "Gets  model By ID",
            description = "Gets model. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find", description = "Find By ID operations")
    @GetMapping("/{modelId}")
    public ResponseEntity<ModelDTO> getModelById(@PathVariable Long manufacturerId,
                                                 @PathVariable Long modelId) {
        ModelDTO modelDTO = modelService.findById(manufacturerId, modelId);
        return ResponseEntity.ok(modelDTO);
    }

    @Operation(
            summary = "Gets all models",
            description = "Gets all models. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find All", description = "Find All  operations")
    @GetMapping
    public ResponseEntity<List<ModelDTO>> getAllModels(@PathVariable Long manufacturerId) {
        List<ModelDTO> models = modelService.getAllModelsByManufacturer(manufacturerId);
        return ResponseEntity.ok(models);
    }

}
