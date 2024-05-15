package com.example.pensionat.repositories;

import com.example.pensionat.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerIdAndEndDateAfter(Long customerId, LocalDate date);
    List<Booking> findByStartDateLessThanAndEndDateGreaterThanAndIdNot(LocalDate endDate, LocalDate startDate,  Long id);
    List<Booking> findByCustomerIdAndEndDateIsGreaterThanAndEndDateIsLessThan(Long customerId, LocalDate date, LocalDate now);
}