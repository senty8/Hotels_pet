package com.senty.hotels_pet.service;

import com.senty.hotels_pet.dto.hotel.CreateHotelRequestDto;
import com.senty.hotels_pet.dto.hotel.CreateHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.LongHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.ShortHotelResponseDto;
import com.senty.hotels_pet.entity.Address;
import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.entity.Hotel;
import com.senty.hotels_pet.mapper.HotelMapper;
import com.senty.hotels_pet.repository.HotelRepository;
import com.senty.hotels_pet.strategy.histogram.HistogramStrategy;
import com.senty.hotels_pet.strategy.histogram.HistogramStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Spy
    private HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Mock
    private AmenityService amenityService;

    @Mock
    private HistogramStrategyFactory histogramStrategyFactory;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;
    private CreateHotelRequestDto createHotelRequestDto;
    private Set<String> amenitiesNames;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setHouseNumber(1234);
        address.setStreet("Street");
        address.setCity("City");
        address.setCountry("Country");
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel 1");
        hotel.setAddress(address);

        createHotelRequestDto = new CreateHotelRequestDto();
        createHotelRequestDto.setName("Hotel 1");

        amenitiesNames = new HashSet<>(Arrays.asList("Pool", "Gym"));
    }

    @Test
    void testGetAllHotels() {
        List<Hotel> hotels = Collections.singletonList(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);

        List<ShortHotelResponseDto> result = hotelService.getAllHotels();

        assertEquals(hotelMapper.toShortHotelResponseDtoList(hotels), result);
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void testGetHotelDtoById() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        LongHotelResponseDto result = hotelService.getHotelDtoById(1L);

        assertEquals(hotelMapper.toLongHotelResponseDto(hotel), result);
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void testGetHotelDtoById_notFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> hotelService.getHotelDtoById(1L));
    }

    @Test
    void testCreateHotel() {
        CreateHotelResponseDto createHotelResponseDto = hotelMapper.toCreateHotelResponseDto(hotel);
        createHotelResponseDto.setId(1L);

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        CreateHotelResponseDto result = hotelService.createHotel(createHotelRequestDto);

        assertEquals(createHotelResponseDto, result);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void testAddAmenitiesToHotel() {
        Amenity amenity = new Amenity();
        amenity.setName("Pool");
        Set<Amenity> amenitiesByName = Collections.singleton(amenity);
        hotel.setAmenities(new HashSet<>());

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(amenityService.getAmenitiesByNames(amenitiesNames)).thenReturn(amenitiesByName);

        hotelService.addAmenitiesToHotel(1L, amenitiesNames);

        assertTrue(hotel.getAmenities().contains(amenity));
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(amenityService, times(1)).getAmenitiesByNames(amenitiesNames);
    }

    @Test
    void testGetHistogram() {
        HistogramStrategy strategy = mock(HistogramStrategy.class);
        Map<String, Long> histogram = new HashMap<>();
        histogram.put("param1", 10L);

        when(histogramStrategyFactory.getStrategy("param")).thenReturn(strategy);
        when(strategy.produce()).thenReturn(histogram);

        Map<String, Long> result = hotelService.getHistogram("param");

        assertEquals(10L, result.get("param1"));
        verify(histogramStrategyFactory, times(1)).getStrategy("param");
        verify(strategy, times(1)).produce();
    }
}