package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.*;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.repositories.BookingRepo;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.repositories.OrderLineRepo;
import com.example.pensionat.services.interfaces.CustomerService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class BookingServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private RoomServicelmpl roomService;
    @Mock
    private OrderLineServicelmpl orderLineService;
    @Mock
    private OrderLineRepo orderLineRepo;
//    @InjectMocks
    BookingServiceImpl bookingServiceImpl;

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
    BookingData bookingData;
    List<OrderLineDTO> rooms;
    RoomDTO r1;
    RoomDTO r2;
    RoomDTO r3;
    SimpleCustomerDTO mockCustomer;

    @BeforeEach
    void setUp() {
        bookingServiceImpl = new BookingServiceImpl(bookingRepo, customerService, roomService, orderLineService, orderLineRepo);
        bookingData = new BookingData();
        bookingData.setEmail("test@example.com");
        bookingData.setStartDate("2024-05-20");
        bookingData.setEndDate("2024-05-21");

        mockCustomer = new SimpleCustomerDTO();
        mockCustomer.setId(1L);

        r1 = new RoomDTO(1L, RoomType.SINGLE);
        r2 = new RoomDTO(2L, RoomType.DOUBLE);
        r3 = new RoomDTO(3L, RoomType.PREMIUM);

        OrderLineDTO room1 = new OrderLineDTO();
        room1.setId(1);
        room1.setRoomType("Single");
        room1.setExtraBeds(1);

        OrderLineDTO room2 = new OrderLineDTO();
        room2.setId(2);
        room2.setRoomType("Double");
        room2.setExtraBeds(1);

        chosenRooms = new ArrayList<>();
        chosenRooms.add(room1);
        chosenRooms.add(room2);

        bookingData.setChosenRooms(chosenRooms);

        when(roomService.getRoomByID(1L)).thenReturn(r1);
        when(roomService.getRoomByID(2L)).thenReturn(r2);
        when(customerService.getCustomerByEmail("test@example.com")).thenReturn(mockCustomer);
    }

    @Test
    void getAllBookings() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerService,
                roomService, orderLineService, orderLineRepo);
        List<DetailedBookingDTO> actual = service.getAllBookings();
        assertEquals(1, actual.size());
    }

    @Test
    void addBooking() {
        booking.setId(id);
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerService,
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
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerService,
                roomService, orderLineService, orderLineRepo);
        DetailedBookingDTO actual = service.getBookingById(id);
        assertEquals(actual.getId(), id);
    }

    @Test
    void removeBookingById() {
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerService,
                roomService, orderLineService, orderLineRepo);
        String feedback = service.removeBookingById(id);
        assertTrue(feedback.equalsIgnoreCase("Booking removed successfully"));
    }

    @Test
    void getBookingByCustomerId() {
        List<Booking> list = List.of(booking);
        when(bookingRepo.findByCustomerIdAndEndDateAfter(customer.getId(), LocalDate.now()))
                .thenReturn(list);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerService,
                roomService, orderLineService, orderLineRepo);
        boolean feedback = service.getBookingByCustomerId(customer.getId());
        assertTrue(feedback);
    }

    @Test
    void generatePriceTest() {

    }

    @Test
    void generatePrice_WithNoDiscount(){
        // Mock booking repository to return an empty list
        when(bookingRepo.findByCustomerIdAndEndDateIsGreaterThanAndEndDateIsLessThan(anyLong(), any(), any())).thenReturn(new ArrayList<>());

        double price = bookingServiceImpl.generatePrice(bookingData);

        double expectedSum = 1 * (500.0 + 750.0);
        System.out.println("Expected price: " + expectedSum);
        System.out.println("Calculated price: " + price);

        assertEquals(expectedSum, price, 0.01);
    }

    @Test
    void generatePrice_WithMultipleNightsDiscount() {
        bookingData.setEndDate("2024-05-22");

        double price = bookingServiceImpl.generatePrice(bookingData);

        double expectedSum = 2 * (500.0 + 750.0) * 0.995; // Two nights for both rooms
        System.out.println("Expected price: " + expectedSum);
        System.out.println("Calculated price: " + price);

        assertEquals(expectedSum, price, 0.01);
    }

    @Test
    void generatePrice_WithTenNightsOrMoreDiscount() {
        Customer customer1 = Mockito.mock(Customer.class);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1L, customer1, LocalDate.now().minusDays(30), LocalDate.now().minusDays(20), new ArrayList<>()));

        when(bookingRepo.findByCustomerIdAndEndDateIsGreaterThanAndEndDateIsLessThan(anyLong(), any(), any())).thenReturn(bookings);

        double price = bookingServiceImpl.generatePrice(bookingData);

        double expectedSum = (500.0 + 750.0) - (500.0 + 750.0) * 0.02;
        System.out.println("Expected price: " + expectedSum);
        System.out.println("Calculated price: " + price);

        assertEquals(expectedSum, price, 0.01);
    }

    @Test
    void generatePrice_WithSundayToMondayDiscount() {
        bookingData.setStartDate("2019-05-19");
        bookingData.setEndDate("2019-05-20");

        double price = bookingServiceImpl.generatePrice(bookingData);

        double expectedSum = (500.0 + 750.0) - (500.0 + 750.0) * 0.02;
        System.out.println("Expected price: " + expectedSum);
        System.out.println("Calculated price: " + price);

        assertEquals(expectedSum, price, 0.01);
    }

    @Test //Kan förmodligen skippa detta test då det tekniskt sätt testas även nedan, men behåller för nu
    void generatePrice_WithSundayToMondayAndMultipleNightsDiscount() {
        bookingData.setStartDate("2024-05-19");
        bookingData.setEndDate("2024-05-22");

        double price = bookingServiceImpl.generatePrice(bookingData);

        double expectedSum = (2 * (500.0 + 750.0) + (500.0 + 750.0) * 0.98) * 0.995;
        System.out.println("Expected price: " + expectedSum);
        System.out.println("Calculated price: " + price);

        assertEquals(expectedSum, price, 0.01);
    }

    @Test
    void generatePrice_WithAllDiscounts() {
        bookingData.setStartDate("2024-05-19");
        bookingData.setEndDate("2024-05-22");

        Customer customer1 = Mockito.mock(Customer.class);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1L, customer1, LocalDate.now().minusDays(30), LocalDate.now().minusDays(25), new ArrayList<>()));
        bookings.add(new Booking(1L, customer1, LocalDate.now().minusDays(40), LocalDate.now().minusDays(35), new ArrayList<>()));

        when(bookingRepo.findByCustomerIdAndEndDateIsGreaterThanAndEndDateIsLessThan(anyLong(), any(), any())).thenReturn(bookings);

        double price = bookingServiceImpl.generatePrice(bookingData);

        double expectedSum = (2 * (500.0 + 750.0) + (500.0 + 750.0) * 0.98) * 0.975;
        System.out.println("Expected price: " + expectedSum);
        System.out.println("Calculated price: " + price);

        assertEquals(expectedSum, price, 0.01);
    }
}