package com.senty.hotels_pet.filter.hotel;

import com.senty.hotels_pet.dto.hotel.HotelFiltersDto;
import com.senty.hotels_pet.entity.Address;
import com.senty.hotels_pet.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HotelCityFilter implements HotelFilter {
    @Override
    public Specification<Hotel> apply(HotelFiltersDto filters) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(filters.getCity())) {
                return cb.like(cb.lower(root.get(Hotel.Fields.address).get(Address.Fields.city)),
                        "%" + filters.getCity().toLowerCase() + "%");
            }
            return null;
        };
    }
}
