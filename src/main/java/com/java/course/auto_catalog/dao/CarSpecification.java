package com.java.course.auto_catalog.dao;

import com.java.course.auto_catalog.model.Car;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class CarSpecification {
    public static Specification<Car> filterByManufacturer(Long manufacturerId) {
        return (root, query, cb) -> cb.equal(root.get("manufacturer").get("id"), manufacturerId);
    }

    public static Specification<Car> filterByModel(Long modelId) {
        return (root, query, cb) -> cb.equal(root.get("model").get("id"), modelId);
    }

    public static Specification<Car> filterByYearRange(Integer minYear, Integer maxYear) {
        return (root, query, cb) -> {
            if (minYear == null && maxYear == null) {
                return cb.conjunction();
            }
            if (minYear != null && maxYear != null) {
                return cb.between(root.get("year"), minYear, maxYear);
            } else if (minYear != null) {
                return cb.greaterThanOrEqualTo(root.get("year"), minYear);
            } else {
                return cb.lessThanOrEqualTo(root.get("year"), maxYear);
            }
        };
    }

    public static Specification<Car> filterByCategories(Set<Long> categoryIds) {
        return (root, query, cb) -> root.join("categories").get("id").in(categoryIds);
    }
}


