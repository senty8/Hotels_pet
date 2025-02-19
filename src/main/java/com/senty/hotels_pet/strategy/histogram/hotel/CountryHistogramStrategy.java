package com.senty.hotels_pet.strategy.hotel;

import com.senty.hotels_pet.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CountryHotelHistogramStrategy implements HotelHistogramStrategy {
    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> produce() {
        return hotelRepository.countByCountries();
    }

    @Override
    public String getName() {
        return "country";
    }
}
