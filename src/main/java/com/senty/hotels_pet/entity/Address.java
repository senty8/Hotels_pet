package com.senty.hotels_pet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "house_number", nullable = false)
    private int houseNumber;

    @Column(name = "street", nullable = false, length = 128)
    private String street;

    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Column(name = "country", nullable = false, length = 64)
    private String country;

    @Column(name = "post_code", nullable = false, length = 32)
    private String postCode;

    @OneToOne(mappedBy = "address")
    private Hotel hotel;

    @Override
    public String toString() {
        return String.format("%d %s, %s, %s, %s", houseNumber, street, city, postCode, country);
    }
}
