package com.senty.hotels_pet.strategy.histogram.hotel;

import com.senty.hotels_pet.repository.HotelRepository;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.BeforeEach;
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
class CityHistogramStrategyTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private CityHistogramStrategy cityHistogramStrategy;

    @BeforeEach
    void setUp() {
        cityHistogramStrategy = new CityHistogramStrategy(hotelRepository);
    }

    @Test
    void produce_shouldReturnCityHistogram() {
        Tuple tuple1 = mock(Tuple.class);
        Tuple tuple2 = mock(Tuple.class);

        when(tuple1.get(0, String.class)).thenReturn("New York");
        when(tuple1.get(1, Long.class)).thenReturn(15L);

        when(tuple2.get(0, String.class)).thenReturn("Berlin");
        when(tuple2.get(1, Long.class)).thenReturn(7L);

        when(hotelRepository.countByCities()).thenReturn(List.of(tuple1, tuple2));

        Map<String, Long> result = cityHistogramStrategy.produce();

        assertEquals(2, result.size());
        assertEquals(15L, result.get("New York"));
        assertEquals(7L, result.get("Berlin"));

        verify(hotelRepository, times(1)).countByCities();
    }

    @Test
    void produce_shouldReturnEmptyMapWhenNoData() {
        when(hotelRepository.countByCities()).thenReturn(List.of());

        Map<String, Long> result = cityHistogramStrategy.produce();

        assertTrue(result.isEmpty());

        verify(hotelRepository, times(1)).countByCities();
    }

    @Test
    void getName_shouldReturnCorrectName() {
        assertEquals("city", cityHistogramStrategy.getName());
    }
}