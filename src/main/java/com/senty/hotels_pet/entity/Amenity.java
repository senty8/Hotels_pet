package com.senty.hotels_pet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Entity
@Data
@Table(name = "amenity")
@FieldNameConstants
public class Amenity {
    public static final String TABLE_NAME = "amenity";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    public Amenity(String name) {
        this.name = name;
    }

    public Amenity() {
    }
}
