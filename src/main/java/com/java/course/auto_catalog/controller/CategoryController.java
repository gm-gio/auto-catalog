package com.java.course.auto_catalog.controller;

import com.java.course.auto_catalog.dto.CategoryDTO;
import com.java.course.auto_catalog.dto.CategoryUpdateDTO;
import com.java.course.auto_catalog.dto.CategoryWithoutIdDTO;
import com.java.course.auto_catalog.service.CategoryService;
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
@RequestMapping("/api/v1/cars/{carId}/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @Operation(
            summary = "Create a new Category",
            description = "Creates a new Category. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Create", description = "Create operations")
    @PostMapping("/new")
    public ResponseEntity<CategoryDTO> save(@PathVariable Long carId, @RequestBody CategoryWithoutIdDTO categoryWithoutIdDTO){

        CategoryDTO savedCategory = categoryService.save(carId, categoryWithoutIdDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    @Operation(
            summary = "Deletes Category by ID",
            description = "Deletes Category by ID. Requires Bearer Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Delete", description = "Delete By ID operations")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteById(@PathVariable Long carId, @PathVariable Long categoryId){
        categoryService.deleteById(carId, categoryId);

        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Update Category",
            description = "Update Category, Requires Beaver Token Authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Update", description = "Update operations")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long carId,
                                                      @PathVariable Long categoryId,
                                                      @RequestBody CategoryUpdateDTO categoryUpdateDTO){
        CategoryDTO updatedCategory = categoryService.updateCategory(carId, categoryId, categoryUpdateDTO);
        return ResponseEntity.ok(updatedCategory);
    }
    @Operation(
            summary = "Gets  Category By ID",
            description = "Gets Category. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find", description = "Find By ID operations")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long carId, @PathVariable Long categoryId){
        CategoryDTO categoryDTO = categoryService.findById(carId, categoryId);
        return ResponseEntity.ok(categoryDTO);
    }
    @Operation(
            summary = "Gets all Categories",
            description = "Gets all Categories. No authentication required.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "Find All", description = "Find All  operations")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll(@PathVariable Long carId){
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategoryByCars(carId);
        return ResponseEntity.ok(categoryDTOS);
    }
}
