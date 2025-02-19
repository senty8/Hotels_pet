package com.senty.hotels_pet.dto.hotel.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateAddressRequestDto {

    @Schema(example = "9")
    @Positive(message = "House number must be a positive integer")
    private int houseNumber;

    @Schema(example = "Pobediteley Avenue")
    @NotBlank(message = "Street name cannot be blank")
    @Size(min = 1, max = 128, message = "Street must be between 1 and 128 characters")
    private String street;

    @Schema(example = "Minsk")
    @NotBlank(message = "City name cannot be blank")
    @Size(min = 1, max = 64, message = "City must be between 1 and 64 characters")
    private String city;

    @Schema(example = "Belarus")
    @NotBlank(message = "Country name cannot be blank")
    @Size(min = 1, max = 64, message = "Country must be between 1 and 64 characters")
    private String country;

    @Schema(example = "220004")
    @NotBlank(message = "Post code cannot be blank")
    @Size(min = 1, max = 32, message = "Post code must be between 1 and 32 characters")
    private String postCode;
}