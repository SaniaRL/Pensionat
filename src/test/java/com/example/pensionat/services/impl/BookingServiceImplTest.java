package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.*;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.BookingRepo;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.repositories.OrderLineRepo;
import com.example.pensionat.services.interfaces.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class BookingServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private CustomerService customerService;
    @Mock
    private RoomServicelmpl roomService;
    @Mock
    private OrderLineServicelmpl orderLineService;
    @Mock
    private OrderLineRepo orderLineRepo;

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
    OrderLineDTO orderLineDTO = new OrderLineDTO(1, "Double", 1);
    List<OrderLineDTO> chosenRooms = List.of(orderLineDTO);
    DetailedOrderLineDTO detailedOrderLineDTO = new DetailedOrderLineDTO();
    BookingData bookingData = new BookingData();

    @Test
    void getAllBookings() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService, orderLineRepo);
        List<DetailedBookingDTO> actual = service.getAllBookings();
        assertEquals(1, actual.size());
    }

    @Test
    void addBooking() {
        booking.setId(id);
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService, orderLineRepo);
        DetailedBookingDTO actual = service.addBooking(detailedBookingDTO);
        assertEquals(actual.getId(), detailedBookingDTO.getId());
        assertEquals(actual.getStartDate(), detailedBookingDTO.getStartDate());
        assertEquals(actual.getEndDate(), detailedBookingDTO.getEndDate());
    }

    @Test
    void getBookingById() {
        booking.setId(id);
        when(bookingRepo.findById(id)).thenReturn(Optional.of(booking));
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService, orderLineRepo);
        DetailedBookingDTO actual = service.getBookingById(id);
        assertEquals(actual.getId(), id);
    }

    @Test
    void removeBookingById() {
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService, orderLineRepo);
        String feedback = service.removeBookingById(id);
        assertTrue(feedback.equalsIgnoreCase("Booking removed successfully"));
    }

    @Test
    void getBookingByCustomerId() {
        List<Booking> list = List.of(booking);
        when(bookingRepo.findByCustomerIdAndEndDateAfter(customer.getId(), LocalDate.now()))
                                                            .thenReturn(list);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService, orderLineRepo);
        boolean feedback = service.getBookingByCustomerId(customer.getId());
        assertTrue(feedback);
    }
}