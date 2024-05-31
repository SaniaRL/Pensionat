package com.example.pensionat.controllers;

import com.example.pensionat.dtos.customer.SimpleCustomerDTO;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", roles={"USER", "ADMIN"})
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerController controller;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BookRoomController testController;

    @MockBean
    private JavaMailSender emailSender;

    String email = "gudrun@disco.com";
    SimpleCustomerDTO customer = new SimpleCustomerDTO("evert.persson@outbook.com", "Evert Persson");
    Long customerId = 1L;

    private void mockAddToModel(Model model, Page<SimpleCustomerDTO> page) {
        model.addAttribute("allCustomers", page.getContent());
        model.addAttribute("currentPage", page.getNumber() + 1);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        Page<SimpleCustomerDTO> mockPage = new PageImpl<>(List.of(customer));
        when(customerService.getCustomerByEmail(anyString())).thenReturn(customer);
        when(customerService.removeCustomerById(anyLong())).thenReturn("Kund borta");

        doAnswer(invocation -> {
            Model model = invocation.getArgument(1);
            mockAddToModel(model, mockPage);
            return null;
        }).when(customerService).addToModel(anyInt(), any(Model.class));

        doAnswer(invocation -> {
            String email = invocation.getArgument(0);
            int currentPage = invocation.getArgument(1);
            Model model = invocation.getArgument(2);
            mockAddToModel(model, new PageImpl<>(List.of(new SimpleCustomerDTO("Evert Karlsson", email)), PageRequest.of(currentPage - 1, 5), 1));
            return null;
        }).when(customerService).addToModelEmail(anyString(), anyInt(), any(Model.class));
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void removeCustomerByIdHandlerWithActiveBookings() throws Exception {
        when(bookingService.getBookingByCustomerId(customerId)).thenReturn(true); //när if sats är sann i controller

        this.mvc.perform(get("/customer/{id}/removeHandler", customerId))
                .andExpect(redirectedUrl("/customer/"))
                .andExpect(flash().attributeExists("status"))
                .andExpect(flash().attribute("status", "En kund kan inte tas bort om det finns aktiva bokningar"));
    }

    @Test
    void removeCustomerByIdHandlerNoActiveBookings() throws Exception {
        when(bookingService.getBookingByCustomerId(customerId)).thenReturn(false); //när if sats är falsk

        this.mvc.perform(get("/customer/{id}/removeHandler", customerId))
                .andExpect(redirectedUrl("/customer/"));
    }

    @Test
    void updateCustomerHandler() throws Exception {
        this.mvc.perform(get("/customer/{email}", email))
                .andExpect(status().isOk()) //200 ok status hehe, ovan funka.
                .andExpect(view().name("updateCustomers"))
                .andExpect(model().attributeExists("kund"));
    }

    @Test
    void handleCustomersUpdate_shouldPass() throws Exception {
        SimpleCustomerDTO customer = new SimpleCustomerDTO("Test Customer", "test@example.com");
        when(customerService.updateCustomer(customer)).thenReturn("OK");
        this.mvc.perform(post("/customer/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", customer.getEmail())
                        .param("name", customer.getName()))
                .andExpect(redirectedUrl("/customer/"));

        verify(customerService, times(1)).updateCustomer(any(SimpleCustomerDTO.class));
        verify(customerService, times(1)).addToModel(eq(1), any(Model.class));
    }

//    @Test
//    void handleCustomersUpdate_shouldNotPass() throws Exception {
//        SimpleCustomerDTO customer = new SimpleCustomerDTO("Test Customer", "test@example.com");
//        customer.setId(1L);
//        SimpleCustomerDTO returnedCustomer = new SimpleCustomerDTO();
//        returnedCustomer.setId(1L);
//        returnedCustomer.setEmail("existing@example.com");
//
//        when(customerService.updateCustomer(any(SimpleCustomerDTO.class))).thenReturn("Email already in use");
//        when(customerService.getCustomerById(1L)).thenReturn(returnedCustomer);
//
//        this.mvc.perform(post("/customer/update")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("email", customer.getEmail())
//                        .param("name", customer.getName()))
//                .andExpect(redirectedUrl("/customer/test@example.com"))
//                .andExpect(flash().attribute("status", "Email already in use"));
//
//        verify(customerService, times(1)).updateCustomer(any(SimpleCustomerDTO.class));
//        verify(customerService, times(1)).getCustomerById(1L);
//    }

    @Test
    void loadCustomerOrNot() throws Exception {
        this.mvc.perform(get("/customer/customerOrNot"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerOrNot"));
    }

    @Test
    void handleCustomers() throws Exception {
        this.mvc.perform(get("/customer/"))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }

    @Test
    void handleByPage() throws Exception {
        int currentPage = 2;
        this.mvc.perform(get("/customer/?page={pageNumber}", currentPage))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }

    @Test
    void getCustomerByEmail() throws Exception {
        this.mvc.perform(get("/customer/")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }

    @Test
    void getCustomerByEmailByPage() throws Exception {
        int currentPage = 3;
        this.mvc.perform(get("/customer/")
                        .param("search", email)
                        .param("page", String.valueOf(currentPage)))
                .andExpect(status().isOk())
                .andExpect(view().name("handleCustomers"))
                .andExpect(model().attributeExists("allCustomers", "currentPage", "totalItems", "totalPages"));
    }
}
