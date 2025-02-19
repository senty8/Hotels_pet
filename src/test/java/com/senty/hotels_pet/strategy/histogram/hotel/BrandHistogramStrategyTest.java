package com.senty.hotels_pet.strategy.histogram.hotel;

import com.senty.hotels_pet.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandHistogramStrategyTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private BrandHistogramStrategy brandHistogramStrategy;

    @Test
    void produce_shouldReturnBrandHistogram() {
        Tuple tuple1 = mock(Tuple.class);
        Tuple tuple2 = mock(Tuple.class);

        when(tuple1.get(0, String.class)).thenReturn("Hilton");
        when(tuple1.get(1, Long.class)).thenReturn(20L);

        when(tuple2.get(0, String.class)).thenReturn("Marriott");
        when(tuple2.get(1, Long.class)).thenReturn(10L);

        when(hotelRepository.countByBrands()).thenReturn(List.of(tuple1, tuple2));

        Map<String, Long> result = brandHistogramStrategy.produce();

        assertEquals(2, result.size());
        assertEquals(20L, result.get("Hilton"));
        assertEquals(10L, result.get("Marriott"));

        verify(hotelRepository, times(1)).countByBrands();
    }

    @Test
    void produce_shouldReturnEmptyMapWhenNoData() {
        when(hotelRepository.countByBrands()).thenReturn(List.of());

        Map<String, Long> result = brandHistogramStrategy.produce();

        assertTrue(result.isEmpty());

        verify(hotelRepository, times(1)).countByBrands();
    }

    @Test
    void getName_shouldReturnCorrectName() {
        assertEquals("brand", brandHistogramStrategy.getName());
    }
}