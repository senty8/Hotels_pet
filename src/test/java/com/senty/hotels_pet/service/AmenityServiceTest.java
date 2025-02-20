package com.senty.hotels_pet.service;

import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.repository.AmenityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmenityServiceTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityService amenityService;

    private Set<String> amenitiesNames;
    private Set<Amenity> amenitiesToSave;

    @BeforeEach
    void setUp() {
        amenitiesNames = new HashSet<>(Arrays.asList("Pool", "Gym", "Spa"));
        amenitiesToSave = amenitiesNames.stream()
                .map(Amenity::new)
                .collect(Collectors.toSet());
    }

    @Test
    void testGetAmenitiesByNames_existingAmenities() {
        Set<Amenity> existingAmenities = new HashSet<>();
        Amenity poolAmenity = new Amenity();
        poolAmenity.setName("Pool");
        existingAmenities.add(poolAmenity);

        amenitiesToSave.remove(poolAmenity);

        when(amenityRepository.findByNameIn(amenitiesNames)).thenReturn(existingAmenities);
        when(amenityRepository.saveAll(amenitiesToSave)).thenReturn(amenitiesToSave.stream().toList());

        Set<Amenity> result = amenityService.getAmenitiesByNames(new HashSet<>(amenitiesNames));

        assertEquals(amenitiesNames, result.stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet()));

        verify(amenityRepository, times(1)).findByNameIn(anySet());
        verify(amenityRepository, times(1)).saveAll(anySet());
    }

    @Test
    void testGetAmenitiesByNames_allNewAmenities() {
        Set<Amenity> existingAmenities = new HashSet<>();

        when(amenityRepository.findByNameIn(amenitiesNames)).thenReturn(existingAmenities);
        when(amenityRepository.saveAll(amenitiesToSave)).thenReturn(amenitiesToSave.stream().toList());

        Set<Amenity> result = amenityService.getAmenitiesByNames(amenitiesNames);

        assertEquals(amenitiesToSave, result);

        verify(amenityRepository, times(1)).findByNameIn(anySet());
        verify(amenityRepository, times(1)).saveAll(anySet());
    }

    @Test
    void testGetAmenitiesByNames_noNewAmenities() {
        Set<Amenity> existingAmenities = new HashSet<>(amenitiesToSave);
        amenitiesToSave.clear();

        when(amenityRepository.findByNameIn(amenitiesNames)).thenReturn(existingAmenities);
        when(amenityRepository.saveAll(amenitiesToSave)).thenReturn(amenitiesToSave.stream().toList());

        Set<Amenity> result = amenityService.getAmenitiesByNames(amenitiesNames);

        assertEquals(existingAmenities, result);

        verify(amenityRepository, times(1)).findByNameIn(anySet());
        verify(amenityRepository, times(1)).saveAll(anySet());
    }
}