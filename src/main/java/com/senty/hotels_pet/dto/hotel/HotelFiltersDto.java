package com.senty.hotels_pet.dto.hotel;

import lombok.Data;

import java.util.Set;

@Data
public class HotelFiltersDto {
    private String name;
    private String brand;
    private String city;
    private String country;
    private Set<String> amenities;
}
