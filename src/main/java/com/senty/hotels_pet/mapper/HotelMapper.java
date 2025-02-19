package com.senty.hotels_pet.mapper;

import com.senty.hotels_pet.dto.hotel.CreateHotelRequestDto;
import com.senty.hotels_pet.dto.hotel.CreateHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.LongHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.ShortHotelResponseDto;
import com.senty.hotels_pet.entity.Address;
import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "contacts.phone")
    ShortHotelResponseDto toShortHotelResponseDto(Hotel hotel);

    List<ShortHotelResponseDto> toShortHotelResponseDtoList(List<Hotel> hotels);

    @Mapping(target = "amenities", source = "amenities", qualifiedByName = "amenitiesSetToStringList")
    LongHotelResponseDto toLongHotelResponseDto(Hotel hotel);

    Hotel toHotel(CreateHotelRequestDto createHotelRequestDto);

    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "contacts.phone")
    CreateHotelResponseDto toCreateHotelResponseDto(Hotel save);

    @Named("amenitiesSetToStringList")
    default List<String> amenitiesSetToStringList(Set<Amenity> amenities) {
        return amenities.stream()
                .map(Amenity::getName)
                .toList();
    }
    default String mapAddressToString(Address address) {
        return address.toString();
    }
}
