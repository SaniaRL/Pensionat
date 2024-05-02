package com.example.pensionat.controllers;

import com.example.pensionat.dtos.*;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.services.impl.BookingServiceImpl;
import com.example.pensionat.services.impl.CustomerServiceImpl;
import com.example.pensionat.services.impl.OrderLineServicelmpl;
import com.example.pensionat.services.impl.RoomServicelmpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class BookRoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRoomController controller;

    @MockBean
    private RoomServicelmpl roomService;

    @MockBean
    private BookingServiceImpl bookingService;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private OrderLineServicelmpl orderLineService;

    OrderLineDTO orderLineDTO = new OrderLineDTO(1, "DOUBLE", 1);
    Long id = 1L;
    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusDays(3);

    Customer customer = new Customer(name, email);
    CustomerDTO customerDto = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(id, name, email);
    Booking booking = new Booking(customer, startDate, endDate);
    DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO(id, simpleCustomerDTO, startDate, endDate);
    BookingDTO bookingDto = new BookingDTO(customerDto, startDate, endDate);
    RoomDTO roomDTO = new RoomDTO(401L, RoomType.DOUBLE);
    List<RoomDTO> rooms = List.of(roomDTO);
    SimpleOrderLineDTO simpleOrderLineDTO = new SimpleOrderLineDTO(booking.getId(), roomDTO, 1);

    /*@BeforeEach
    void init() {

    } */

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

        when(roomService.findAvailableRooms(query)).thenReturn(rooms);
        this.mvc.perform(post("/bookingSubmit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("startDate", query.getStartDate().toString())
                        .param("endDate", query.getEndDate().toString())
                        .param("rooms", String.valueOf(query.getRooms()))
                        .param("beds", String.valueOf(query.getBeds())))
                .andExpect(status().isOk())
                .andExpect(view().name("Booking"));
    }
    /*
    @PostMapping("/bookingSubmit")
    public String processBookingForm(@ModelAttribute BookingFormQueryDTO query, Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        String status = "Error: Query is null";

        if (query != null) {
            availableRooms = roomService.findAvailableRooms(query);
            status = roomService.enoughRooms(query, availableRooms);
            model.addAttribute("startDate", query.getStartDate());
            model.addAttribute("endDate", query.getEndDate());
            model.addAttribute("rooms", query.getRooms());
            model.addAttribute("beds", query.getBeds());
        }

        if(status.isEmpty()){
            model.addAttribute("availableRooms", availableRooms);
        }

        model.addAttribute("chosenRooms", chosenRooms);
        model.addAttribute("status", status);

        return "booking";
    }
     */

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
                        .andExpect(status().isFound())
                        .andExpect(redirectedUrl("/bookingConfirmation"));
    }

    @Test
    void showBookingConfirmation() throws Exception{
        mvc.perform(get("/bookingConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookingConfirmation"));
    }
}