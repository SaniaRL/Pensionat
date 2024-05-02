package com.example.pensionat.repositories;

import com.example.pensionat.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;


public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerIdAndEndDateAfter(Long customerId, LocalDate date);
}