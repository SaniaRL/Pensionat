package com.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
/**
 * LocalDate startDate
 * LocalDate endDate
 * int beds
 * int rooms
 * List<SimpleOrderLineDTO> chosenRooms
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedBookingFormQueryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int beds;
    private int rooms;
    private List<SimpleOrderLineDTO> chosenRooms;
}