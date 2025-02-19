package com.senty.hotels_pet.strategy.histogram.hotel;

import com.senty.hotels_pet.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
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
class CountryHistogramStrategyTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private CountryHistogramStrategy countryHistogramStrategy;

    @BeforeEach
    void setUp() {
        countryHistogramStrategy = new CountryHistogramStrategy(hotelRepository);
    }

    @Test
    void produce_shouldReturnCountryHistogram() {
        Tuple tuple1 = mock(Tuple.class);
        Tuple tuple2 = mock(Tuple.class);

        when(tuple1.get(0, String.class)).thenReturn("USA");
        when(tuple1.get(1, Long.class)).thenReturn(10L);

        when(tuple2.get(0, String.class)).thenReturn("Germany");
        when(tuple2.get(1, Long.class)).thenReturn(5L);

        when(hotelRepository.countByCountries()).thenReturn(List.of(tuple1, tuple2));

        Map<String, Long> result = countryHistogramStrategy.produce();

        assertEquals(2, result.size());
        assertEquals(10L, result.get("USA"));
        assertEquals(5L, result.get("Germany"));

        verify(hotelRepository, times(1)).countByCountries();
    }

    @Test
    void produce_shouldReturnEmptyMapWhenNoData() {
        when(hotelRepository.countByCountries()).thenReturn(List.of());

        Map<String, Long> result = countryHistogramStrategy.produce();

        assertTrue(result.isEmpty());

        verify(hotelRepository, times(1)).countByCountries();
    }

    @Test
    void getName_shouldReturnCorrectName() {
        assertEquals("country", countryHistogramStrategy.getName());
    }
}