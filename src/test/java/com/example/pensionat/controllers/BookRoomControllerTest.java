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

import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        //TODO måste göra
    }

    @Test
    void booking() throws Exception {
        mvc.perform(get("/booking"))
                .andExpect(status().isOk())
                .andExpect(view().name("booking"));
    }

    @Test
    void updateBooking() throws Exception {
        mvc.perform(post("/booking")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booking"))
                .andExpect(model().attribute("bookingId", 1));
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