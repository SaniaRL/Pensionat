package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.BookingData;
import com.example.pensionat.dtos.booking.DetailedBookingDTO;


import java.util.List;

public interface BookingService {

    List<DetailedBookingDTO> getAllBookings();
    DetailedBookingDTO addBooking(DetailedBookingDTO b);
    DetailedBookingDTO updateBooking(DetailedBookingDTO b);
    String removeBookingById(Long id);
    DetailedBookingDTO getBookingById(Long id);
    boolean getBookingByCustomerId(Long customerId);
    List<Integer> submitBookingCustomer(BookingData bookingData);
    int getNumberOfRoomsFromBooking(Long id);
    int getNumberOfBedsFromBooking(Long id);

    double generatePrice(BookingData bookingData);

    }