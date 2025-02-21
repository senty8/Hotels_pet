package com.senty.hotels_pet.service;

import com.senty.hotels_pet.dto.hotel.CreateHotelRequestDto;
import com.senty.hotels_pet.dto.hotel.CreateHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.HotelFiltersDto;
import com.senty.hotels_pet.dto.hotel.LongHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.ShortHotelResponseDto;
import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.entity.Hotel;
import com.senty.hotels_pet.filter.hotel.HotelFilter;
import com.senty.hotels_pet.mapper.HotelMapper;
import com.senty.hotels_pet.repository.HotelRepository;
import com.senty.hotels_pet.strategy.histogram.HistogramStrategy;
import com.senty.hotels_pet.strategy.histogram.HistogramStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final Set<HotelFilter> hotelFilters;
    private final AmenityService amenityService;
    private final HistogramStrategyFactory histogramStrategyFactory;

    public List<ShortHotelResponseDto> getAllHotels() {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<ShortHotelResponseDto> shortHotelResponseDtoList = hotelMapper
                .toShortHotelResponseDtoList(allHotels);
        log.info("All hotels returned");
        return shortHotelResponseDtoList;
    }

    public LongHotelResponseDto getHotelDtoById(long id) {
        Hotel hotel = getHotelById(id);
        LongHotelResponseDto longHotelResponseDto = hotelMapper.toLongHotelResponseDto(hotel);
        log.info("Hotel with id {} returned", id);
        return longHotelResponseDto;
    }

    public Hotel getHotelById(long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + id + " not found"));
        log.info("Hotel with id {} found", id);
        return hotel;
    }

    public List<ShortHotelResponseDto> getFilteredHotels(HotelFiltersDto hotelFiltersDto) {
        Specification<Hotel> spec = hotelFilters.stream()
                .map(filter -> filter.apply(hotelFiltersDto))
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
        List<Hotel> filteredHotels = hotelRepository.findAll(spec);

        List<ShortHotelResponseDto> shortHotelResponseDtoList = hotelMapper
                .toShortHotelResponseDtoList(filteredHotels);
        log.info("Filtered hotels returned");
        return shortHotelResponseDtoList;
    }

    public CreateHotelResponseDto createHotel(CreateHotelRequestDto createHotelRequestDto) {
        Hotel hotel = hotelMapper.toHotel(createHotelRequestDto);
        Hotel savedHotel = hotelRepository.save(hotel);
        CreateHotelResponseDto createHotelResponseDto = hotelMapper
                .toCreateHotelResponseDto(savedHotel);
        log.info("Created hotel with id {} returned", createHotelResponseDto.getId());
        return createHotelResponseDto;
    }

    public void addAmenitiesToHotel(long id, Set<String> amenitiesNames) {
        Set<Amenity> amenitiesByName = amenityService.getAmenitiesByNames(amenitiesNames);

        Hotel hotel = getHotelById(id);
        hotel.setAmenities(amenitiesByName);

        hotelRepository.save(hotel);
        log.info("Added amenities to hotel with id {}", id);
    }

    public Map<String, Long> getHistogram(String param) {
        HistogramStrategy strategy = histogramStrategyFactory.getStrategy(param);
        Map<String, Long> produce = strategy.produce();
        log.info("Histogram strategy produced for {}", param);
        return produce;
    }
}
