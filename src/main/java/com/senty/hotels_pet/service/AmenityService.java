package com.senty.hotels_pet.service;

import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AmenityService {
    private final AmenityRepository amenityRepository;

    public Set<Amenity> getAmenitiesByNames(Set<String> amenitiesNames) {
        Set<Amenity> amenitiesInDatabase = amenityRepository.findByNameIn(amenitiesNames);

        Set<String> amenitiesNamesInDatabase = amenitiesInDatabase.stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet());

        amenitiesNames.removeAll(amenitiesNamesInDatabase);
        Set<Amenity> newAmenities = amenitiesNames.stream()
                .map(name -> {
                    Amenity amenity = new Amenity();
                    amenity.setName(name);
                    return amenity;
                })
                .collect(Collectors.toSet());
        List<Amenity> savedAmenities = amenityRepository.saveAll(newAmenities);
        amenitiesInDatabase.addAll(savedAmenities);
        return amenitiesInDatabase;
    }
}
