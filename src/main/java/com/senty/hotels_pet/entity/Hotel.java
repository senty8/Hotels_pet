package com.senty.hotels_pet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "hotel")
@FieldNameConstants
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "brand", nullable = false, length = 128)
    private String brand;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "contacts_id", nullable = false)
    private Contacts contacts;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "arrival_time_id", nullable = false)
    private ArrivalTime arrivalTime;

    @ManyToMany
    @JoinTable(name = "hotel_amenity",
    joinColumns = @JoinColumn(name = "hotel_id"),
    inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<Amenity> amenities = new HashSet<>();
}
