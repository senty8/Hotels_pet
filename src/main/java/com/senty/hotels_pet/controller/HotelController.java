package com.senty.hotels_pet.controller;

import com.senty.hotels_pet.dto.hotel.CreateHotelRequestDto;
import com.senty.hotels_pet.dto.hotel.CreateHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.HotelFiltersDto;
import com.senty.hotels_pet.dto.hotel.LongHotelResponseDto;
import com.senty.hotels_pet.dto.hotel.ShortHotelResponseDto;
import com.senty.hotels_pet.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/hotels")
    public List<ShortHotelResponseDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    public LongHotelResponseDto getHotelById(@PathVariable long id) {
        return hotelService.getHotelDtoById(id);
    }

    @GetMapping("/search")
    public List<ShortHotelResponseDto> getFilteredHotels(HotelFiltersDto hotelFiltersDto) {
        return hotelService.getFilteredHotels(hotelFiltersDto);
    }

    @PostMapping("/hotels")
    public CreateHotelResponseDto createHotel(@RequestBody CreateHotelRequestDto createHotelRequestDto) {
        return hotelService.createHotel(createHotelRequestDto);
    }

    @PostMapping("/hotels/{id}/amenities")
    public void addAmenitiesToHotel(@PathVariable long id,
                                                    @RequestBody Set<String> amenitiesNames) {
        hotelService.addAmenitiesToHotel(id, amenitiesNames);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
