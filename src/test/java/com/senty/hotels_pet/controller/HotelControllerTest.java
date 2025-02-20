package com.senty.hotels_pet.controller;

import com.senty.hotels_pet.dto.hotel.CreateHotelRequestDto;
import com.senty.hotels_pet.dto.hotel.CreateHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.HotelFiltersDto;
import com.senty.hotels_pet.dto.hotel.LongHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.ShortHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.address.AddressResponseDto;
import com.senty.hotels_pet.dto.hotel.address.CreateAddressRequestDto;
import com.senty.hotels_pet.dto.hotel.arrival_time.ArrivalTimeResponseDto;
import com.senty.hotels_pet.dto.hotel.arrival_time.CreateArrivalTimeRequestDto;
import com.senty.hotels_pet.dto.hotel.contacts.ContactsResponseDto;
import com.senty.hotels_pet.dto.hotel.contacts.CreateContactsRequestDto;
import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.entity.Hotel;
import com.senty.hotels_pet.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@ActiveProfiles("test")
class HotelControllerTest {
    @Autowired
    private HotelController hotelController;
    @Autowired
    private HotelRepository hotelRepository;

    private ShortHotelResponseDto shortHotelResponseDto;
    private LongHotelResponseDto longHotelResponseDto;
    private Set<String> amenities;

    @BeforeEach
    void setUp() {
        shortHotelResponseDto = new ShortHotelResponseDto();
        shortHotelResponseDto.setId(1L);
        shortHotelResponseDto.setName("DoubleTree by Hilton Minsk");
        shortHotelResponseDto.setDescription("The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...");
        shortHotelResponseDto.setAddress("9 Pobediteley Avenue, Minsk, 220004, Belarus");
        shortHotelResponseDto.setPhone("+375 17 309-80-00");

        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setHouseNumber(9);
        addressResponseDto.setStreet("Pobediteley Avenue");
        addressResponseDto.setCity("Minsk");
        addressResponseDto.setCountry("Belarus");
        addressResponseDto.setPostCode("220004");

        ContactsResponseDto contactsResponseDto = new ContactsResponseDto();
        contactsResponseDto.setPhone("+375 17 309-80-00");
        contactsResponseDto.setEmail("doubletreeminsk.info@hilton.com");

        ArrivalTimeResponseDto arrivalTimeResponseDto = new ArrivalTimeResponseDto();
        arrivalTimeResponseDto.setCheckIn(LocalTime.of(14, 0));
        arrivalTimeResponseDto.setCheckOut(LocalTime.of(12, 0));

        amenities = Set.of("Free parking",
                "Free WiFi",
                "Non-smoking rooms",
                "Concierge",
                "On-site restaurant",
                "Fitness center",
                "Pet-friendly rooms",
                "Room service",
                "Business center",
                "Meeting rooms");

        longHotelResponseDto = new LongHotelResponseDto();
        longHotelResponseDto.setId(1L);
        longHotelResponseDto.setName("DoubleTree by Hilton Minsk");
        longHotelResponseDto.setBrand("Hilton");
        longHotelResponseDto.setAddress(addressResponseDto);
        longHotelResponseDto.setContacts(contactsResponseDto);
        longHotelResponseDto.setArrivalTime(arrivalTimeResponseDto);
        longHotelResponseDto.setAmenities(amenities);
    }

    @Test
    void getAllHotelsTest() {
        List<ShortHotelResponseDto> allHotels = hotelController.getAllHotels();

        ShortHotelResponseDto resultDto = allHotels.stream().filter(dto -> dto.getId() == 1)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        assertThat(resultDto).isEqualTo(shortHotelResponseDto);
        assertThat(allHotels).hasSize(3);
    }

    @Test
    void getHotelByIdTest() {
        LongHotelResponseDto hotelById = hotelController.getHotelById(1);

        assertThat(hotelById).isEqualTo(longHotelResponseDto);
    }

    @Test
    void getFilteredHotelsTest_filterByName() {
        HotelFiltersDto hotelFiltersDto = new HotelFiltersDto();
        hotelFiltersDto.setName("Resort");

        List<ShortHotelResponseDto> filteredHotels = hotelController.getFilteredHotels(hotelFiltersDto);

        assertThat(filteredHotels)
                .hasSize(2)
                .allMatch(hotel -> hotel.getName().toLowerCase().contains("Resort".toLowerCase()));
    }

    @Test
    void getFilteredHotelsTest_filterByBrand() {
        HotelFiltersDto hotelFiltersDto = new HotelFiltersDto();
        hotelFiltersDto.setBrand("Marriott");

        List<ShortHotelResponseDto> filteredHotels = hotelController.getFilteredHotels(hotelFiltersDto);

        assertThat(filteredHotels)
                .hasSize(2)
                .doesNotContain(shortHotelResponseDto);
    }

    @Test
    void getFilteredHotelsTest_filterByCity() {
        HotelFiltersDto hotelFiltersDto = new HotelFiltersDto();
        hotelFiltersDto.setCity("New York");

        List<ShortHotelResponseDto> filteredHotels = hotelController.getFilteredHotels(hotelFiltersDto);

        assertThat(filteredHotels)
                .hasSize(2)
                .allMatch(hotel -> hotel
                        .getAddress()
                        .split(",")[1]
                        .toLowerCase()
                        .contains("New York".toLowerCase()));
    }

    @Test
    void getFilteredHotelsTest_filterByCountry() {
        HotelFiltersDto hotelFiltersDto = new HotelFiltersDto();
        hotelFiltersDto.setCountry("USA");

        List<ShortHotelResponseDto> filteredHotels = hotelController.getFilteredHotels(hotelFiltersDto);

        assertThat(filteredHotels)
                .hasSize(2)
                .allMatch(hotel -> hotel
                        .getAddress()
                        .split(",")[3]
                        .toLowerCase()
                        .contains("USA".toLowerCase()));
    }

    @Test
    void getFilteredHotelsTest_filterByAmenities() {
        Set<String> amenities = Set.of("Play Station 5");
        HotelFiltersDto hotelFiltersDto = new HotelFiltersDto();
        hotelFiltersDto.setAmenities(amenities);

        List<ShortHotelResponseDto> filteredHotels = hotelController.getFilteredHotels(hotelFiltersDto);

        assertThat(filteredHotels)
                .hasSize(2)
                .doesNotContain(shortHotelResponseDto);
    }

    @Test
    @Transactional
    void createHotelTest() {
        CreateHotelRequestDto input = getCreateHotelRequestDto();
        CreateHotelResponseDto expected = getCreateHotelResponseDto();

        CreateHotelResponseDto hotel = hotelController.createHotel(input);
        Hotel hotelFromRepository = hotelRepository.findById(expected.getId()).orElseGet(null);

        assertThat(hotelFromRepository)
                .isNotNull()
                .matches(hotelInDatabase -> hotelInDatabase.getName().equals(expected.getName()));

        assertThat(hotel)
                .isEqualTo(expected);
    }

    private static CreateHotelResponseDto getCreateHotelResponseDto() {
        CreateHotelResponseDto createHotelResponseDto = new CreateHotelResponseDto();
        createHotelResponseDto.setId(4L);
        createHotelResponseDto.setName("DoubleTree by Hilton Minsk");
        createHotelResponseDto.setDescription("The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...");
        createHotelResponseDto.setAddress("9 Pobediteley Avenue, Minsk, 220004, Belarus");
        createHotelResponseDto.setPhone("+375 17 309-80-00");
        return createHotelResponseDto;
    }

    private static CreateHotelRequestDto getCreateHotelRequestDto() {
        CreateAddressRequestDto createAddressRequestDto = new CreateAddressRequestDto();
        createAddressRequestDto.setHouseNumber(9);
        createAddressRequestDto.setStreet("Pobediteley Avenue");
        createAddressRequestDto.setCity("Minsk");
        createAddressRequestDto.setCountry("Belarus");
        createAddressRequestDto.setPostCode("220004");

        CreateContactsRequestDto createContactsRequestDto = new CreateContactsRequestDto();
        createContactsRequestDto.setPhone("+375 17 309-80-00");
        createContactsRequestDto.setEmail("doubletreeminsk.info@hilton.com");

        CreateArrivalTimeRequestDto createArrivalTimeRequestDto = new CreateArrivalTimeRequestDto();
        createArrivalTimeRequestDto.setCheckIn(LocalTime.of(14, 0));
        createArrivalTimeRequestDto.setCheckOut(LocalTime.of(12, 0));

        CreateHotelRequestDto createHotelRequestDto = new CreateHotelRequestDto();
        createHotelRequestDto.setName("DoubleTree by Hilton Minsk");
        createHotelRequestDto.setDescription("The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...");
        createHotelRequestDto.setBrand("Hilton");
        createHotelRequestDto.setAddress(createAddressRequestDto);
        createHotelRequestDto.setContacts(createContactsRequestDto);
        createHotelRequestDto.setArrivalTime(createArrivalTimeRequestDto);
        return createHotelRequestDto;
    }

    @Test
    @Transactional
    void addAmenitiesToHotelTest() {

        Set<String> newAmenities = new HashSet<>(amenities);
        newAmenities.remove("Free WiFi");
        newAmenities.add("Personal computer");
        Set<String> expectedSet = new HashSet<>(newAmenities);

        hotelController.addAmenitiesToHotel(1, newAmenities);
        Hotel hotel = hotelRepository.findById(1L).orElse(null);

        assertThat(hotel).isNotNull();
        assertThat(hotel.getAmenities()
                .stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet()))
                .isEqualTo(expectedSet);

    }

    @Test
    void getHistogramTest_ByBrand() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Hilton", 1L);
        expected.put("Marriott", 2L);

        Map<String, Long> result = hotelController.getHistogram("brand");

        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void getHistogramTest_ByCity() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Minsk", 1L);
        expected.put("New York", 2L);

        Map<String, Long> result = hotelController.getHistogram("city");

        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void getHistogramTest_ByCountry() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Belarus", 1L);
        expected.put("USA", 2L);

        Map<String, Long> result = hotelController.getHistogram("country");

        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void getHistogramTest_ByAmenities() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Free parking", 2L);
        expected.put("Free WiFi", 2L);
        expected.put("Non-smoking rooms", 1L);
        expected.put("Concierge", 2L);
        expected.put("On-site restaurant", 2L);
        expected.put("Fitness center", 1L);
        expected.put("Pet-friendly rooms", 1L);
        expected.put("Room service", 1L);
        expected.put("Business center", 1L);
        expected.put("Meeting rooms", 1L);
        expected.put("Play Station 5", 2L);

        Map<String, Long> result = hotelController.getHistogram("amenities");

        assertThat(result)
                .isEqualTo(expected);
    }
}