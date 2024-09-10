package com.java.course.auto_catalog.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarUpdateDTO {
    Integer carYear;
    ManufacturerDTO manufacturer;
    ModelDTO model;
    Set<CategoryDTO> categories;
}
