package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.BookingDTO;
import com.example.pensionat.dtos.BookingData;
import com.example.pensionat.dtos.DetailedBookingDTO;


import java.util.List;

public interface BookingService {

    List<DetailedBookingDTO> getAllBookings();
    DetailedBookingDTO addBooking(DetailedBookingDTO b);
    String removeBookingById(Long id);
    DetailedBookingDTO getBookingById(Long id);
    boolean getBookingByCustomerId(Long customerId);
    String submitBookingCustomer(BookingData bookingData);
    int getNumberOfRoomsFromBooking(Long id);
    int getNumberOfBedsFromBooking(Long id);
    }