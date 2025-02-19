package com.senty.hotels_pet.strategy.histogram.hotel;

import com.senty.hotels_pet.repository.HotelRepository;
import com.senty.hotels_pet.strategy.histogram.HistogramStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CityHistogramStrategy implements HistogramStrategy {
    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> produce() {
        return hotelRepository.countByCities()
                .stream()
                .collect(Collectors.toMap(tuple -> tuple.get(0, String.class),
                        tuple -> tuple.get(1, Long.class)));
    }

    @Override
    public String getName() {
        return "city";
    }
}
