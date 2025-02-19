package com.senty.hotels_pet.dto.hotel.arrival_time;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateArrivalTimeRequestDto {
    @Schema(example = "14:00", type = "string", pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull
    public LocalTime checkIn;

    @Schema(example = "14:00", type = "string", pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    public LocalTime checkOut;
}
