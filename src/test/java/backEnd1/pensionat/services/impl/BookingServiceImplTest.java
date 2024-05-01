package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.*;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.convert.BookingConverter;
import backEnd1.pensionat.services.convert.CustomerConverter;
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
        List<Booking> list = List.of(booking);
        when(bookingRepo.findByCustomerIdAndEndDateAfter(customer.getId(), LocalDate.now()))
                                                            .thenReturn(list);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        boolean feedback = service.getBookingByCustomerId(customer.getId());
        assertTrue(feedback);
    }

    @Test //Funkar ej. Behöver fixas.
    void submitBookingCustomer() {
        booking.setId(1L);
        SimpleCustomerDTO existingCustomer = new SimpleCustomerDTO(name, email);
        when(customerService.getCustomerByEmail(email)).thenReturn(existingCustomer);

        when(customerService.getCustomerByEmail(email)).thenReturn(simpleCustomerDTO);
        when(orderLineService.addOrderLine(any(DetailedOrderLineDTO.class))).thenReturn("Hejhopp");
        when(BookingConverter.bookingToDetailedBookingDTO(booking)).thenReturn(detailedBookingDTO);
        when(bookingRepo.save(booking)).thenReturn(booking);
        bookingData.setName(name);
        bookingData.setEmail(email);
        bookingData.setStartDate("2024-05-01");
        bookingData.setEndDate("2024-05-04");
        bookingData.setChosenRooms(chosenRooms);
        BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, customerService,
                                                            roomService, orderLineService);
        String feedback = service.submitBookingCustomer(bookingData);
        assertTrue(feedback.equalsIgnoreCase("Everything is fine"));
    }
    /*
    public String submitBookingCustomer(BookingData bookingData) {
        String name = bookingData.getName();
        String email = bookingData.getEmail();
        List<OrderLineDTO> orderLines = bookingData.getChosenRooms();
        LocalDate startDate = LocalDate.parse(bookingData.getStartDate());
        LocalDate endDate = LocalDate.parse(bookingData.getEndDate());

        System.out.println();
        System.out.println("Namn: " + name);
        System.out.println("Email: " + email);
        System.out.println("Startdatum: " + bookingData.getStartDate());
        System.out.println("Slutdatum: " + bookingData.getEndDate());
        System.out.println("Valda rum: ");
        for (OrderLineDTO room : orderLines) {
            System.out.println("  - RumID: " + room.getId() + "  - Rumstyp: " + room.getRoomType() + ", Extra sängar: " + room.getExtraBeds());
        }
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println();

        //Kolla om kunden finns - hämta kund eller skapa ny
        SimpleCustomerDTO customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            customer = new SimpleCustomerDTO(name, email);
            //Add customer to Repo
            customer = customerService.addCustomer(customer);
            System.out.println("New customer added: " + customer);
        }

        //Skapa bokning
        DetailedBookingDTO booking = new DetailedBookingDTO(customer, startDate, endDate);
        System.out.println("New booking: " + booking);

        //Lägg till bokning i DATABAS och spara om den
        booking = addBooking(booking);
        System.out.println("Added booking: " + booking);

        //TODO Uppdatera Customer lägg till bokning

        //TODO Översätt totala rum till extra rum - rum???? menar säng

        //Lägg till orderrader?
        //TODO har inte ändrat dessa till DTO det bråkar inte med nåt:
        DetailedBookingDTO finalBooking = booking;
        orderLines.stream()
                .map(orderLine -> new DetailedOrderLineDTO(orderLine.getExtraBeds(), finalBooking, roomService.getRoomByID((long) orderLine.getId())))
                .forEach(orderLineService::addOrderLine);

        return "Everything is fine";
    }
     */
}