package com.senty.hotels_pet.dto.hotel;

import com.senty.hotels_pet.dto.hotel.address.CreateAddressRequestDto;
import com.senty.hotels_pet.dto.hotel.arrival_time.CreateArrivalTimeRequestDto;
import com.senty.hotels_pet.dto.hotel.contacts.CreateContactsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateHotelRequestDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 128, message = "Name must be between 1 and 128 characters")
    @Schema(example = "DoubleTree by Hilton Minsk")
    private String name;

    @Size(max = 4096, message = "Description must be less than or equal to 4096 characters")
    @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital " +
            "and stunning views of Minsk city from the hotel's 20th floor ...")
    private String description;

    @NotBlank(message = "Brand cannot be blank")
    @Size(min = 1, max = 128, message = "Brand must be between 1 and 128 characters")
    @Schema(example = "Hilton")
    private String brand;

    @NotNull(message = "Address cannot be null")
    private CreateAddressRequestDto address;

    @NotNull(message = "Contacts cannot be null")
    private CreateContactsRequestDto contacts;

    @NotNull(message = "Arrival time cannot be null")
    private CreateArrivalTimeRequestDto arrivalTime;
}