package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.booking.DetailedBookingDTO;
import com.example.pensionat.dtos.customer.SimpleCustomerDTO;
import com.example.pensionat.dtos.orderline.DetailedOrderLineDTO;
import com.example.pensionat.dtos.orderline.SimpleOrderLineDTO;
import com.example.pensionat.dtos.room.RoomDTO;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.models.Room;
import com.example.pensionat.repositories.BookingRepo;
import com.example.pensionat.repositories.OrderLineRepo;
import com.example.pensionat.services.impl.OrderLineServicelmpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class OrderLineServicelmplTest {

    @Mock
    private OrderLineRepo orderLineRepo;
    @Mock
    private BookingRepo bookingRepo;

    @MockBean
    private JavaMailSender emailSender;

    Long id = 1L;
    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.parse("2024-05-14");
    LocalDate endDate = LocalDate.parse("2024-05-17");
    Room room = new Room(301L, 2);
    RoomDTO roomDTO = new RoomDTO(401L, RoomType.DOUBLE);
    int extraBeds = 0;

    Customer customer = new Customer(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(id, name, email);
    Booking booking = new Booking(customer, startDate, endDate);
    DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO(1L, simpleCustomerDTO, startDate, endDate);
    Long orderLineId = 3L;
    OrderLine orderLine = new OrderLine(orderLineId, booking, room, 2);
    SimpleOrderLineDTO simpleOrderLineDTO = new SimpleOrderLineDTO(booking.getId(), roomDTO, 1);
    DetailedOrderLineDTO detailedOrderLineDTO = new DetailedOrderLineDTO(extraBeds, detailedBookingDTO, roomDTO);


    @Test
    void getAllOrderLines() {
        when(orderLineRepo.findAll()).thenReturn(Arrays.asList(orderLine));
        OrderLineServicelmpl service = new OrderLineServicelmpl(orderLineRepo, bookingRepo);
        List<SimpleOrderLineDTO> actual = service.getAllOrderLines();
        assertEquals(1, actual.size());
    }

    @Test
    void addOrderLineWithOrderLineParam() {
        OrderLineServicelmpl service = new OrderLineServicelmpl(orderLineRepo, bookingRepo);
        String feedback = service.addOrderLine(orderLine);
        assertTrue(feedback.equalsIgnoreCase("Orderline added"));
    }

    @Test
    void addOrderLineWithDetailedOrderLineDTOParam() {
        OrderLineServicelmpl service = new OrderLineServicelmpl(orderLineRepo, bookingRepo);
        String feedback = service.addOrderLine(detailedOrderLineDTO);
        assertTrue(feedback.equalsIgnoreCase("Orderline added"));
    }

    @Test
    void removeOrderLineById() {
        OrderLineServicelmpl service = new OrderLineServicelmpl(orderLineRepo, bookingRepo);
        String feedback = service.removeOrderLineById(orderLineId);
        assertTrue(feedback.equalsIgnoreCase("Room " + orderLineId + " removed"));
    }

    @Test
    void getOrderLinesByBookingId() {
        when(orderLineRepo.findAll()).thenReturn(Arrays.asList(orderLine));
        OrderLineServicelmpl service = new OrderLineServicelmpl(orderLineRepo, bookingRepo);
        List<SimpleOrderLineDTO> actual = service.getOrderLinesByBookingId(booking.getId());
        assertEquals(1, actual.size());
        assertEquals(orderLine.getBooking().getId(), actual.get(0).getBookingId());
    }
}