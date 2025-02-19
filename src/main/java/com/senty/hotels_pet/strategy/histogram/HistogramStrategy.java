package com.senty.hotels_pet.strategy.histogram;

import java.util.Map;

public interface HistogramStrategy {
    Map<String, Long> produce();

    String getName();
}
