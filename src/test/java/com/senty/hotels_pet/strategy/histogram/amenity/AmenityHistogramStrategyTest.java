package com.senty.hotels_pet.strategy.histogram.amenity;

import com.senty.hotels_pet.repository.AmenityRepository;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmenityHistogramStrategyTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityHistogramStrategy amenityHistogramStrategy;

    @Test
    void produce_shouldReturnAmenityHistogram() {
        Tuple tuple1 = mock(Tuple.class);
        Tuple tuple2 = mock(Tuple.class);

        when(tuple1.get(0, String.class)).thenReturn("Pool");
        when(tuple1.get(1, Long.class)).thenReturn(15L);

        when(tuple2.get(0, String.class)).thenReturn("Gym");
        when(tuple2.get(1, Long.class)).thenReturn(10L);

        when(amenityRepository.countByName()).thenReturn(List.of(tuple1, tuple2));

        Map<String, Long> result = amenityHistogramStrategy.produce();

        assertEquals(2, result.size());
        assertEquals(15L, result.get("Pool"));
        assertEquals(10L, result.get("Gym"));

        verify(amenityRepository, times(1)).countByName();
    }

    @Test
    void produce_shouldReturnEmptyMapWhenNoData() {
        when(amenityRepository.countByName()).thenReturn(List.of());

        Map<String, Long> result = amenityHistogramStrategy.produce();

        assertTrue(result.isEmpty());

        verify(amenityRepository, times(1)).countByName();
    }

    @Test
    void getName_shouldReturnCorrectName() {
        assertEquals("amenities", amenityHistogramStrategy.getName());
    }
}