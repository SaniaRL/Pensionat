package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.convert.BookingConverter;
import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public
class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final CustomerService customerService;

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, CustomerService customerService) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.customerService = customerService;
    }

    @Override
    public List<DetailedBookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(BookingConverter::bookingToDetailedBookingDTO)
                .toList();
    }

    @Override
    public Booking addBooking(Booking b) {
        Booking savedBooking = bookingRepo.save(b);
        return savedBooking;
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
        return bookingToDetailedBookingDto(bookingRepo.findById(id).orElse(null));
    }

    @Override
    public DetailedBookingDTO bookingToDetailedBookingDto(Booking b) {
        return DetailedBookingDTO.builder().id(b.getId())
                .customer(customerService.customerToSimpleCustomerDto(b.getCustomer()))
                .startDate(b.getStartDate()).endDate(b.getEndDate()).build();
    }

    @Override
    public String removeBookingById(Long id) {
        bookingRepo.deleteById(id);
        return "Booking removed successfully";
    }

    @Override
    public Booking bookingDtoToBooking(BookingDTO b, Customer c) {
        return Booking.builder().customer(c).startDate(b.getStartDate()).endDate(b.getEndDate()).build();
    }

 /*   @Override
    public boolean getBookingByCustomerId(Long customerId) {
        List<Booking> bookings = bookingRepo.findByCustomerId(customerId);
        return !bookings.isEmpty();
    }*/


    public boolean getBookingByCustomerId(Long customerId) {
        LocalDate today = LocalDate.now();
        List<Booking> activeBookings  = bookingRepo.findByCustomerIdAndEndDateAfter(customerId, today);
        return !activeBookings .isEmpty();  // Returns true if there are future bookings
    }


}
