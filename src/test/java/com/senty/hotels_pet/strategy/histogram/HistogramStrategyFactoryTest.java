package com.senty.hotels_pet.strategy.histogram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistogramStrategyFactoryTest {

    @Mock
    private HistogramStrategy strategy1;

    @Mock
    private HistogramStrategy strategy2;

    private HistogramStrategyFactory factory;

    @BeforeEach
    void setUp() {
        when(strategy1.getName()).thenReturn("strategy1");
        when(strategy2.getName()).thenReturn("strategy2");

        factory = new HistogramStrategyFactory(List.of(strategy1, strategy2));
    }

    @Test
    void getStrategy_existingStrategy() {
        HistogramStrategy result = factory.getStrategy("strategy1");
        assertEquals(strategy1, result);
    }

    @Test
    void getStrategy_unknownStrategy() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> factory.getStrategy("unknown"));
        assertEquals("Unknown strategy: unknown", exception.getMessage());
    }
}