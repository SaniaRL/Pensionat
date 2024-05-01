package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        when(customerService.getCustomerByEmailSimpleDTO(anyString())).thenReturn(customer);
        when(customerService.removeCustomerById(anyLong())).thenReturn("Customer removed successfully");
    }

    @Test
    void removeCustomerByIdHandler() throws Exception {}

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
        when(customerService.getAllCustomersPage(currentPage)).thenReturn(mockPage);

        mvc.perform(get("/customer/handle"))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }

    @Test
    void handleByPage() throws Exception {
        int currentPage = 1;
        Page<SimpleCustomerDTO> mockPage = new PageImpl<>(List.of(new SimpleCustomerDTO("test@example.com", "Test Customer")));
        when(customerService.getAllCustomersPage(currentPage)).thenReturn(mockPage);

        mvc.perform(get("/customer/handle/{pageNumber}", currentPage))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers.html"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }

    @Test
    void loadFrontPage() throws Exception {
        mvc.perform(get("/customer/frontPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("Index"));
    }


    @Test
    void getCustomerByEmail() throws Exception {

    }

    @Test
    void getCustomerByEmailByPage() throws Exception{

    }




}