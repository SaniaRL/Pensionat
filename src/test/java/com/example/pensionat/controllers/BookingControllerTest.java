package com.example.pensionat.controllers;

import com.example.pensionat.dtos.*;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.BookingRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingRepo mockRepo;

    Long customerId = 1L;
    Long bookingId = 5L;
    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusDays(3);

    Customer customer = new Customer(customerId, name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(customerId, name, email);
    Booking booking = new Booking(bookingId, customer, startDate, endDate, null);
    DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO(bookingId, simpleCustomerDTO,
            startDate, endDate);
    RoomDTO roomDTO = new RoomDTO(401L, RoomType.DOUBLE);
    SimpleOrderLineDTO simpleOrderLineDTO = new SimpleOrderLineDTO(booking.getId(), roomDTO, 1);

    @Test
    void searchBooking() throws Exception {
        this.mvc.perform(get("/booking/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookingSearch"));
    }

    @Test
    void removeBookingById() throws Exception {
        this.mvc.perform(get("/booking/remove"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookingRemoved"));
    }

    @Test
    void updateBooking() throws Exception {
        when(mockRepo.findById(5L)).thenReturn(Optional.of(booking));
        this.mvc.perform(get("/booking/update")
                        .param("id", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateBooking"));
    }

    @Test
    void updateBookingIfBookingNull() throws Exception {
        when(mockRepo.findById(5L)).thenReturn(Optional.of(booking));
        this.mvc.perform(get("/booking/update")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookingSearch"));
    }

    @Test
    void updateConfirm() throws Exception {
        BookingFormQueryDTO query = new BookingFormQueryDTO();
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setRooms(2);
        query.setBeds(4);

        List<SimpleOrderLineDTO> chosenRooms = new ArrayList<>();
        chosenRooms.add(simpleOrderLineDTO);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("chosenRooms", chosenRooms);
        session.setAttribute("booking", detailedBookingDTO);

        this.mvc.perform(post("/booking/updateConfirm")
                        .session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("startDate", query.getStartDate().toString())
                        .param("endDate", query.getEndDate().toString())
                        .param("rooms", String.valueOf(query.getRooms()))
                        .param("beds", String.valueOf(query.getBeds())))
                .andExpect(status().isOk())
                .andExpect(view().name("updateBooking"));
    }
}