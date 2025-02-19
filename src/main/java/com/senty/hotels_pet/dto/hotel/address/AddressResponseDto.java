package com.senty.hotels_pet.dto.hotel.address;

import lombok.Data;

@Data
public class AddressResponseDto {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
