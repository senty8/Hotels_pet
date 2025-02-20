package com.senty.hotels_pet.dto.hotel;

import com.senty.hotels_pet.dto.hotel.address.AddressResponseDto;
import com.senty.hotels_pet.dto.hotel.arrival_time.ArrivalTimeResponseDto;
import com.senty.hotels_pet.dto.hotel.contacts.ContactsResponseDto;
import lombok.Data;

import java.util.Set;

@Data
public class LongHotelResponseDto {
    private long id;
    private String name;
    private String brand;
    private AddressResponseDto address;
    private ContactsResponseDto contacts;
    private ArrivalTimeResponseDto arrivalTime;
    private Set<String> amenities;
}
