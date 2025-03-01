package com.senty.hotels_pet.dto.hotel;

import lombok.Data;

@Data
public class ShortHotelResponseDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
