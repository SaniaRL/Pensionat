package com.example.pensionat.controllers;

import com.example.pensionat.services.impl.BookingServiceImpl;
import com.example.pensionat.services.impl.CustomerServiceImpl;
import com.example.pensionat.services.impl.OrderLineServicelmpl;
import com.example.pensionat.services.impl.RoomServicelmpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class BookRoomControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private RoomServicelmpl roomService;
    @MockBean
    private BookingServiceImpl bookingService;
    @MockBean
    private CustomerServiceImpl customerService;
    @MockBean
    private OrderLineServicelmpl orderLineService;

    /*@BeforeEach
    void init() {

    } */

    @Test
    void processBookingForm() {
        //TODO måste göras
    }

    @Test
    void booking() {
        //TODO måste göras
    }

    @Test
    void updateBooking() {
        //TODO måste göras
    }

    @Test
    void confirmBooking() throws Exception {
        mvc.perform(post("/confirmBooking"))
                .andExpect(redirectedUrl("/customer/customerOrNot"));
    }

    @Test
    void submitBookingCustomer() {
        //TODO måste göras
    }

    @Test
    void showBookingConfirmation() throws Exception{
        mvc.perform(get("/bookingConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookingConfirmation"));
    }
}