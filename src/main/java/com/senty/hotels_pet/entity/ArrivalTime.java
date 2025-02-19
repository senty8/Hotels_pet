package com.senty.hotels_pet.entity;

import com.senty.hotels_pet.config.converter.ArrivalTimeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "arrival_time")
public class ArrivalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "check_in", nullable = false)
    @Convert(converter = ArrivalTimeConverter.class)
    private LocalTime checkIn;

    @Column(name = "check_out")
    @Convert(converter = ArrivalTimeConverter.class)
    private LocalTime checkOut;
}
