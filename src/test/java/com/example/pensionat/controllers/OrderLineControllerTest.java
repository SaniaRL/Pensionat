package com.example.pensionat.controllers;

import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.models.Room;
import com.example.pensionat.repositories.OrderLineRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", roles={"USER", "ADMIN"})
class OrderLineControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderLineController controller;

    @MockBean
    private OrderLineRepo mockRepo;

    Room room1 = new Room(201L, 0, 0, null);

    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.parse("2024-05-14");
    LocalDate endDate = LocalDate.parse("2024-05-17");

    Customer customer = new Customer(name, email);
    Booking booking = new Booking(1L, customer, startDate, endDate, null);
    Long orderLineId = 3L;
    OrderLine orderLine = new OrderLine(orderLineId, booking, room1, 2);

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void getAllOrderLines() throws Exception {
        when(mockRepo.findAll()).thenReturn(Arrays.asList(orderLine));
        this.mvc.perform(get("/orderline/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"bookingId\":1, \"room\": { \"id\":201 ,\"roomType\": \"SINGLE\"}," +
                                            "\"extraBeds\":2}]"));
    }

    @Test
    void addOrderLine() throws Exception {
        this.mvc.perform(post("/orderline/add")
                        .param("name", "Holger Persson")
                        .param("email", "holger@email.com")
                        .param("startDate", "2024-06-14")
                        .param("endDate", "2024-06-20")
                        .param("roomId", "302")
                        .param("typeOfRoom", "0")
                        .param("extraBeds", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("OrderLine added")));
    }

    @Test
    void removeOrderLineById() throws Exception {
        this.mvc.perform(get("/orderline/201/remove"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Room 201 removed")));
    }
}