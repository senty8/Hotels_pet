package com.senty.hotels_pet.strategy.histogram.amenity;

import com.senty.hotels_pet.repository.AmenityRepository;
import com.senty.hotels_pet.strategy.histogram.HistogramStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AmenityHistogramStrategy implements HistogramStrategy {
    private final AmenityRepository amenityRepository;

    @Override
    public Map<String, Long> produce() {
        return amenityRepository.countByName()
                .stream()
                .collect(Collectors.toMap(tuple -> tuple.get(0, String.class),
                        tuple -> tuple.get(1, Long.class)));
    }

    @Override
    public String getName() {
        return "amenities";
    }
}
