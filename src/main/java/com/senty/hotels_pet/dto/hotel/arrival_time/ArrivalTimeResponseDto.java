package com.senty.hotels_pet.dto.hotel.arrival_time;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ArrivalTimeResponseDto {
    @Schema(example = "14:00", type = "string", pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime checkIn;

    @Schema(example = "14:00", type = "string", pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime checkOut;
}
