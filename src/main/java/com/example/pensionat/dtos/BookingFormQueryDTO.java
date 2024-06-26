package com.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**
 * LocalDate startDate
 * LocalDate endDate
 * int beds
 * int rooms
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingFormQueryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int beds;
    private int rooms;

}