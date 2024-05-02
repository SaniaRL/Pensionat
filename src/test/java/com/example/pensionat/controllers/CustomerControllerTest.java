package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private CustomerService customerService;

    @MockBean //VARFÖR FAN BEHÖVS DEANNANWDNANDANDANWDNAWDNADNAWDN
    private BookRoomController test;

    @BeforeEach
    void setUp() {
        SimpleCustomerDTO customer = new SimpleCustomerDTO("test@example.com", "Test Customer");
        when(customerService.getCustomerByEmail(anyString())).thenReturn(customer);
        when(customerService.removeCustomerById(anyLong())).thenReturn("Customer removed successfully");
    }

    @Test
    void removeCustomerByIdHandler() throws Exception {
        //TODO när affärslogik utbruten
    }

    @Test
    void updateCustomerHandler() throws Exception {
        String email = "test@example.com";
        mvc.perform(get("/customer/{email}/update", email))
                .andExpect(status().isOk())
                .andExpect(view().name("updateCustomers"))
                .andExpect(model().attributeExists("kund"));
    }

    @Test
    void handleCustomersUpdate() throws Exception{
        //TODO
    }

    @Test
    void loadCustomerOrNot() throws Exception {
        mvc.perform(get("/customer/customerOrNot"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerOrNot"));
    }

    @Test
    void handleCustomers() throws Exception {
        int currentPage = 1;
        Page<SimpleCustomerDTO> mockPage = new PageImpl<>(List.of(new SimpleCustomerDTO("Test Customer", "test@example.com")));

        // Mock the behavior of customerService.addToModel to manipulate the model as expected
        doAnswer(invocation -> {
            Model model = invocation.getArgument(1);
            model.addAttribute("allCustomers", mockPage.getContent());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", mockPage.getTotalElements());
            model.addAttribute("totalPages", mockPage.getTotalPages());
            return null;
        }).when(customerService).addToModel(eq(currentPage), any(Model.class));

        mvc.perform(get("/customer/handle"))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }


    @Test
    void handleByPage() throws Exception {
        int currentPage = 1;
        Page<SimpleCustomerDTO> mockPage = new PageImpl<>(List.of(new SimpleCustomerDTO("test@example.com", "Test Customer")));

        // Mock the behavior of customerService.addToModel for the specified page number
        doAnswer(invocation -> {
            Model model = invocation.getArgument(1);
            model.addAttribute("allCustomers", mockPage.getContent());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", mockPage.getTotalElements());
            model.addAttribute("totalPages", mockPage.getTotalPages());
            return null;
        }).when(customerService).addToModel(eq(currentPage), any(Model.class));

        mvc.perform(get("/customer/handle/{pageNumber}", currentPage))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }


    @Test
    void getCustomerByEmail() throws Exception {
        //TODO
    }

    @Test
    void getCustomerByEmailByPage() throws Exception{
        //TODO
    }

}