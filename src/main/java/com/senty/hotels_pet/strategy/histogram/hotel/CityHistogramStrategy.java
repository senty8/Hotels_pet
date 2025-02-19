package com.senty.hotels_pet.strategy.hotel;

import com.senty.hotels_pet.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CityHotelHistogramStrategy implements HotelHistogramStrategy {
    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> produce() {
        return hotelRepository.countByCities();
    }

    @Override
    public String getName() {
        return "city";
    }
}
