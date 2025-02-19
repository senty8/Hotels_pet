package com.senty.hotels_pet.strategy.histogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class HistogramStrategyFactory {
    private final Map<String, HistogramStrategy> strategies  = new HashMap<>();

    @Autowired
    public HistogramStrategyFactory(List<HistogramStrategy> strategiesList) {
        for (HistogramStrategy stageDeletionStrategy : strategiesList) {
            strategies.put(stageDeletionStrategy.getName(), stageDeletionStrategy);
        }
    }

    public HistogramStrategy getStrategy(String name) {
        return Optional.ofNullable(strategies.get(name))
                .orElseThrow(() -> new IllegalArgumentException("Unknown strategy: " + name));
    }
}
