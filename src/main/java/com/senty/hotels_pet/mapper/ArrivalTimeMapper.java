package com.senty.hotels_pet.mapper;

import com.senty.hotels_pet.dto.hotel.arrival_time.ArrivalTimeResponseDto;
import com.senty.hotels_pet.dto.hotel.arrival_time.CreateArrivalTimeRequestDto;
import com.senty.hotels_pet.entity.ArrivalTime;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArrivalTimeMapper {

    ArrivalTimeResponseDto toArrivalTimeResponseDto(ArrivalTime arrivalTime);

    ArrivalTime toArrivalTime(CreateArrivalTimeRequestDto createArrivalTimeRequestDto);
}
