package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.*;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.convert.BookingConverter;
import backEnd1.pensionat.services.convert.RoomConverter;
import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.interfaces.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static backEnd1.pensionat.services.convert.BookingConverter.bookingDtoToBooking;
import static backEnd1.pensionat.services.convert.BookingConverter.bookingToDetailedBookingDTO;

@Service
public
class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final RoomServicelmpl roomService;
    private final OrderLineServicelmpl orderLineService;
    @PersistenceContext
    EntityManager entityManager;

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, CustomerService customerService, RoomServicelmpl roomServicelmpl, OrderLineServicelmpl orderLineService) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.customerService = customerService;
        this.roomService = roomServicelmpl;
        this.orderLineService = orderLineService;
    }

    @Override
    public List<DetailedBookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(BookingConverter::bookingToDetailedBookingDTO)
                .toList();
    }

    @Override
    public DetailedBookingDTO addBooking(DetailedBookingDTO b) {
        return bookingToDetailedBookingDTO(bookingRepo.save(BookingConverter.detailedBookingDTOtoBooking(b)));
    }

    @Override
    public Long addBookingFromBookingDto(BookingDTO b) {
        //TODO om kunden inte finns och måste registreras?
        Customer customer = customerRepo.findByEmail(b.getCustomer().getEmail());
        Booking booking = bookingDtoToBooking(b, customer);
        bookingRepo.save(booking);
        return booking.getId();
    }

    @Override
    public DetailedBookingDTO getBookingById(Long id) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if(booking != null){
            return bookingToDetailedBookingDTO(booking);
        }
        return null;
    }

    @Override
    public String removeBookingById(Long id) {
        bookingRepo.deleteById(id);
        return "Booking removed successfully";
    }

    @Override
    public boolean getBookingByCustomerId(Long customerId) {
        LocalDate today = LocalDate.now();
        List<Booking> activeBookings  = bookingRepo.findByCustomerIdAndEndDateAfter(customerId, today);
        return !activeBookings .isEmpty();  // Returns true if there are future bookings
    }

    @Override
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
}