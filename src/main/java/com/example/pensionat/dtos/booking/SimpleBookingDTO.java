package com.example.pensionat.dtos.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleBookingDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
}
