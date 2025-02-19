package com.senty.hotels_pet.filter.hotel;

import com.senty.hotels_pet.dto.hotel.HotelFiltersDto;
import com.senty.hotels_pet.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class HotelAmenitiesFilter implements HotelFilter {
    @Override
    public Specification<Hotel> apply(HotelFiltersDto filters) {
        return (root, query, cb) -> {
            if (!CollectionUtils.isEmpty(filters.getAmenities())) {
                Join<Object, Object> amenitiesJoin = root.join("amenities", JoinType.INNER);
                return amenitiesJoin.get("name").in(filters.getAmenities());
            }
            return null;
        };
    }
}
