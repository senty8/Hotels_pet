package com.senty.hotels_pet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "phone", nullable = false, length = 32)
    private String phone;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @OneToOne(mappedBy = "contacts")
    @ToString.Exclude
    private Hotel hotel;
}
