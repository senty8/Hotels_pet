package com.senty.hotels_pet.mapper;

import com.senty.hotels_pet.dto.hotel.address.AddressResponseDto;
import com.senty.hotels_pet.dto.hotel.address.CreateAddressRequestDto;
import com.senty.hotels_pet.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressResponseDto toAddressResponseDto(Address address);

    Address toAddress(CreateAddressRequestDto createAddressRequestDto);
}
