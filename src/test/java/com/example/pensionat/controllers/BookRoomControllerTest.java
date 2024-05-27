package com.example.pensionat.controllers;

import com.example.pensionat.dtos.*;
import com.example.pensionat.services.impl.BookingServiceImpl;

import com.example.pensionat.services.impl.MailTemplateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", roles={"USER", "ADMIN"})
class BookRoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRoomController controller;

    @MockBean
    private BookingServiceImpl bookingService;

    @MockBean
    private JavaMailSender emailSender;

    @MockBean
    private MailTemplateServiceImpl mailTemplateService;

    @BeforeEach
    void setUp() {
        MimeMessage mockMimeMessage = new MimeMessage((Session) null);
        Mockito.when(emailSender.createMimeMessage()).thenReturn(mockMimeMessage);
    }

    OrderLineDTO orderLineDTO = new OrderLineDTO(1, "DOUBLE", 1);
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusDays(3);

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void processBookingForm() throws Exception {
        BookingFormQueryDTO query = new BookingFormQueryDTO();
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setRooms(2);
        query.setBeds(4);

        this.mvc.perform(post("/bookingSubmit?emptyBooking=false")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("startDate", query.getStartDate().toString())
                        .param("endDate", query.getEndDate().toString())
                        .param("rooms", String.valueOf(query.getRooms()))
                        .param("beds", String.valueOf(query.getBeds())))
                .andExpect(status().isOk())
                .andExpect(view().name("booking"));
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
    void submitBookingCustomer() throws Exception {
        BookingData bookingData = new BookingData();
        bookingData.setStartDate("2024-05-02");
        bookingData.setEndDate("2024-05-03");
        bookingData.setName("John Doe");
        bookingData.setEmail("john@example.com");
        List<OrderLineDTO> chosenRooms = new ArrayList<>();
        chosenRooms.add(orderLineDTO);
        bookingData.setChosenRooms(chosenRooms);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBookingData = objectMapper.writeValueAsString(bookingData);

        mvc.perform(post("/submitBookingCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookingData))
                        .andExpect(status().isOk())
                        .andExpect(view().name("bookingConfirmation"));
    }

    @Test
    void showBookingConfirmation() throws Exception{
        mvc.perform(get("/bookingConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookingConfirmation"));
    }
}