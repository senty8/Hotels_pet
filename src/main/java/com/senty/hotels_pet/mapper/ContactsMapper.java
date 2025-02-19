package com.senty.hotels_pet.mapper;

import com.senty.hotels_pet.dto.hotel.contacts.ContactsResponseDto;
import com.senty.hotels_pet.dto.hotel.contacts.CreateContactsRequestDto;
import com.senty.hotels_pet.entity.Contacts;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactsMapper {

    ContactsResponseDto toContactsResponseDto(Contacts contacts);

    Contacts toContacts(CreateContactsRequestDto createContactsRequestDto);
}
