package com.java.course.auto_catalog.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelUpdateDTO {
    String modelName;
    String modelCategory;
    ManufacturerDTO manufacturer;
}
