package com.senty.hotels_pet.strategy.hotel;

import java.util.Map;

public interface HotelHistogramStrategy {
    Map<String, Long> produce();

    String getName();
}
