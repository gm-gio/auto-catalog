package com.java.course.auto_catalog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;


import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "manufacturers", schema = "auto_catalog")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @Column(name = "name")
    private String manufacturerName;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Model> models;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Manufacturer manufacturer = (Manufacturer) o;

        return manufacturerId != null && manufacturerId.equals(manufacturer.manufacturerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerId);
    }
}
