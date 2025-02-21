package com.senty.hotels_pet.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.senty.hotels_pet.dto.hotel.CreateHotelRequestDto;
import com.senty.hotels_pet.dto.hotel.CreateHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.LongHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.ShortHotelResponseDto;
import com.senty.hotels_pet.entity.Amenity;
import com.senty.hotels_pet.entity.Hotel;
import com.senty.hotels_pet.repository.HotelRepository;
import com.senty.hotels_pet.util.JsonTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HotelControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void getAllHotelsTest() {
        List<ShortHotelResponseDto> expected = JsonTestUtil
                .readJsonFromFile("json/hotel/get_all_hotels_response.json", new TypeReference<>() {});

        List<ShortHotelResponseDto> allHotels = webTestClient.get()
                .uri("/hotels")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShortHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(allHotels)
                .hasSize(3)
                .isEqualTo(expected);
    }

    @Test
    void getHotelByIdTest() {
        LongHotelResponseDto longHotelResponseDto = JsonTestUtil
                .readJsonFromFile("json/hotel/get_hotel_by_id_response.json", new TypeReference<>() {});

        LongHotelResponseDto hotel = webTestClient.get()
                .uri("/hotels/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LongHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(hotel)
                .isEqualTo(longHotelResponseDto);
    }

    @Test
    void getFilteredHotelsTest_filterByName() {
        List<ShortHotelResponseDto> expected = getExpectedFilteredHotels();

        List<ShortHotelResponseDto> filteredHotels = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("name", "Resort")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShortHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(filteredHotels)
                .isEqualTo(expected);
    }

    @Test
    void getFilteredHotelsTest_filterByBrand() {
        List<ShortHotelResponseDto> expected = getExpectedFilteredHotels();

        List<ShortHotelResponseDto> filteredHotels = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("brand", "Marriott")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShortHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(filteredHotels)
                .isEqualTo(expected);
    }

    @Test
    void getFilteredHotelsTest_filterByCity() {
        List<ShortHotelResponseDto> expected = getExpectedFilteredHotels();

        List<ShortHotelResponseDto> filteredHotels = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("city", "New York")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShortHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(filteredHotels)
                .isEqualTo(expected);
    }

    @Test
    void getFilteredHotelsTest_filterByCountry() {
        List<ShortHotelResponseDto> expected = getExpectedFilteredHotels();

        List<ShortHotelResponseDto> filteredHotels = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("country", "USA")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShortHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(filteredHotels)
                .isEqualTo(expected);
    }

    @Test
    void getFilteredHotelsTest_filterByAmenities() {
        List<ShortHotelResponseDto> expected = getExpectedFilteredHotels();

        List<ShortHotelResponseDto> filteredHotels = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("amenities", "Play Station 5")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShortHotelResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(filteredHotels)
                .isEqualTo(expected);
    }

    private List<ShortHotelResponseDto> getExpectedFilteredHotels() {
        return JsonTestUtil.readJsonFromFile("json/hotel/get_filtered_hotels_response.json",
                new TypeReference<>() {});
    }

    @Test
    @Transactional
    @Rollback(false)
    void createHotelTest() {
        CreateHotelRequestDto input = JsonTestUtil.readJsonFromFile("json/hotel/create_hotel_request.json",
                new TypeReference<>() {});
        CreateHotelResponseDto expected = JsonTestUtil.readJsonFromFile("json/hotel/create_hotel_response.json",
                new TypeReference<>() {});

        CreateHotelResponseDto response = webTestClient.post()
                .uri("/hotels")
                .bodyValue(input)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateHotelResponseDto.class)
                .returnResult()
                .getResponseBody();
        Hotel hotelFromRepository = hotelRepository.findById(expected.getId())
                .orElseThrow(NoSuchElementException::new);

        hotelRepository.deleteById(expected.getId());

        assertThat(hotelFromRepository)
                .isNotNull()
                .matches(hotelInDatabase -> hotelInDatabase.getName().equals(expected.getName()));

        assertThat(response)
                .isEqualTo(expected);
    }

    @Test
    @Transactional
    @Rollback(false)
    void addAmenitiesToHotelTest() {
        Set<String> amenitiesBeforeChange = JsonTestUtil.readJsonFromFile("json/hotel/amenities_before_change.json",
                new TypeReference<>() {});

        Set<String> input = JsonTestUtil.readJsonFromFile("json/hotel/add_amenities_request.json",
                new TypeReference<>() {});

        webTestClient.post()
                .uri("/hotels/{id}/amenities", 1)
                .bodyValue(input)
                .exchange()
                .expectStatus().isOk();
        Hotel hotel = hotelRepository.findById(1L).orElse(null);

        assertThat(hotel).isNotNull();
        assertThat(hotel.getAmenities()
                .stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet()))
                .isEqualTo(input);

        webTestClient.post()
                .uri("/hotels/{id}/amenities", 1)
                .bodyValue(amenitiesBeforeChange)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getHistogramTest_ByBrand() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Hilton", 1L);
        expected.put("Marriott", 2L);

        Map<String, Long> result = webTestClient.get()
                .uri("/histogram/{param}", "brand")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, Long>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void getHistogramTest_ByCity() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Minsk", 1L);
        expected.put("New York", 2L);

        Map<String, Long> result = webTestClient.get()
                .uri("/histogram/{param}", "city")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, Long>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void getHistogramTest_ByCountry() {
        HashMap<String, Long> expected = new HashMap<>();
        expected.put("Belarus", 1L);
        expected.put("USA", 2L);

        Map<String, Long> result = webTestClient.get()
                .uri("/histogram/{param}", "country")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, Long>>() {})
                .returnResult()
                .getResponseBody();

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

        Map<String, Long> result = webTestClient.get()
                .uri("/histogram/{param}", "amenities")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, Long>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(result)
                .isEqualTo(expected);
    }
}