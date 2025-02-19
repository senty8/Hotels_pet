package com.senty.hotels_pet.filter.hotel;

import com.senty.hotels_pet.dto.hotel.HotelFiltersDto;
import com.senty.hotels_pet.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public interface HotelFilter {
    Specification<Hotel> apply(HotelFiltersDto filters);
}
