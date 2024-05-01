package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.interfaces.CustomerService;
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

    Long id = 1L;
    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.parse("2024-05-14");
    LocalDate endDate = LocalDate.parse("2024-05-17");

    Customer customer = new Customer(name, email);
    CustomerDTO customerDto = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(id, name, email);
    Booking booking = new Booking(customer, startDate, endDate);
    DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO(id, simpleCustomerDTO, startDate, endDate);
    BookingDTO bookingDto = new BookingDTO(customerDto, startDate, endDate);

    @Test
    void getAllBookings() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        List<DetailedBookingDTO> actual = service.getAllBookings();
        assertEquals(1, actual.size());
    }

    @Test
    void addBooking() {
        booking.setId(id);
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        DetailedBookingDTO actual = service.addBooking(detailedBookingDTO);
        assertEquals(actual.getId(), detailedBookingDTO.getId());
        assertEquals(actual.getStartDate(), detailedBookingDTO.getStartDate());
        assertEquals(actual.getEndDate(), detailedBookingDTO.getEndDate());
    }


    @Test
    void addBookingFromBookingDto() {
        when(customerRepo.findByEmail(booking.getCustomer().getEmail())).thenReturn(customer);
        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking savedBooking = invocation.getArgument(0);
            savedBooking.setId(1L);
            return savedBooking;
        });
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        Long actual = service.addBookingFromBookingDto(bookingDto);
        assertNotNull(actual);
    }

    @Test
    void getBookingById() {
        booking.setId(id);
        when(bookingRepo.findById(id)).thenReturn(Optional.of(booking));
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        DetailedBookingDTO actual = service.getBookingById(id);
        assertEquals(actual.getId(), id);
    }

    @Test
    void removeBookingById() {
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        String feedback = service.removeBookingById(id);
        assertTrue(feedback.equalsIgnoreCase("Booking removed successfully"));
    }

    @Test
    void getBookingByCustomerId() {
    }

    @Test
    void submitBookingCustomer() {
    }
}