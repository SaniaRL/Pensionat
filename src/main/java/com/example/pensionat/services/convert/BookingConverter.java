package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.booking.BookingDTO;
import com.example.pensionat.dtos.booking.DetailedBookingDTO;
import com.example.pensionat.dtos.customer.SimpleCustomerDTO;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    public static DetailedBookingDTO bookingToDetailedBookingDTO(Booking booking) {
        return DetailedBookingDTO.builder().id(booking.getId())
                .customer(new SimpleCustomerDTO(booking.getCustomer().getId(),
                        booking.getCustomer().getName(), booking.getCustomer().getEmail()))
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).build();
    }
    public static Booking bookingDtoToBooking(BookingDTO b, Customer c) {
        return Booking.builder().customer(c).startDate(b.getStartDate()).endDate(b.getEndDate()).build();
    }

    public static Booking detailedBookingDTOtoBooking(DetailedBookingDTO booking) {
        SimpleCustomerDTO customer = booking.getCustomer();
        return Booking.builder().id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customer(Customer.builder().id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail()).build())
                .build();
    }
}