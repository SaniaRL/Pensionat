package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.BookingDTO;
import com.example.pensionat.dtos.CustomerDTO;
import com.example.pensionat.dtos.DetailedBookingDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingConverterTest {

    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.parse("2024-05-14");
    LocalDate endDate = LocalDate.parse("2024-05-17");

    Customer customer = new Customer(name, email);
    CustomerDTO customerDTO = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(name, email);
    Booking booking = new Booking(name, email, startDate, endDate);
    BookingDTO bookingDTO = new BookingDTO(customerDTO, startDate, endDate);
    DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO(simpleCustomerDTO, startDate, endDate);

    @Test
    void bookingToDetailedBookingDTO() {
        DetailedBookingDTO actual = BookingConverter.bookingToDetailedBookingDTO(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());

        assertEquals(actual.getCustomer().getId(), booking.getCustomer().getId());
        assertEquals(actual.getCustomer().getName(), booking.getCustomer().getName());
        assertEquals(actual.getCustomer().getEmail(), booking.getCustomer().getEmail());
    }

    @Test
    void bookingDtoToBooking() {
        Booking actual = BookingConverter.bookingDtoToBooking(bookingDTO, customer);

        assertEquals(actual.getStartDate(), bookingDTO.getStartDate());
        assertEquals(actual.getEndDate(), bookingDTO.getEndDate());

        assertEquals(actual.getCustomer().getId(), customer.getId());
        assertEquals(actual.getCustomer().getName(), customer.getName());
        assertEquals(actual.getCustomer().getEmail(), customer.getEmail());
    }

    @Test
    void detailedBookingDTOtoBooking() {
        Booking actual = BookingConverter.detailedBookingDTOtoBooking(detailedBookingDTO);

        assertEquals(actual.getId(), detailedBookingDTO.getId());
        assertEquals(actual.getStartDate(), detailedBookingDTO.getStartDate());
        assertEquals(actual.getEndDate(), detailedBookingDTO.getEndDate());

        assertEquals(actual.getCustomer().getId(), detailedBookingDTO.getCustomer().getId());
        assertEquals(actual.getCustomer().getName(), detailedBookingDTO.getCustomer().getName());
        assertEquals(actual.getCustomer().getEmail(), detailedBookingDTO.getCustomer().getEmail());
    }
}