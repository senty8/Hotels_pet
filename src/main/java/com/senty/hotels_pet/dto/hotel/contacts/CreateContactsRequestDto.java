package com.senty.hotels_pet.dto.hotel.contacts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateContactsRequestDto {

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 1, max = 32, message = "Phone must be between 1 and 32 characters")
    @Schema(example = "+375 17 309-80-00")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Size(min = 1, max = 64, message = "Email must be between 1 and 64 characters")
    @Schema(example = "doubletreeminsk.info@hilton.com")
    private String email;
}
