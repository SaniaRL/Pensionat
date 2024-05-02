package com.example.pensionat.services.impl;

import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.BookingRepo;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.dtos.*;
import com.example.pensionat.services.convert.BookingConverter;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public
class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final RoomServicelmpl roomService;
    private final OrderLineServicelmpl orderLineService;

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo,
                              CustomerService customerService, RoomServicelmpl roomServicelmpl,
                              OrderLineServicelmpl orderLineService) {
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
        return BookingConverter.bookingToDetailedBookingDTO(bookingRepo.save(BookingConverter.detailedBookingDTOtoBooking(b)));
    }

    @Override
    public DetailedBookingDTO getBookingById(Long id) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if(booking != null){
            return BookingConverter.bookingToDetailedBookingDTO(booking);
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
        return !activeBookings .isEmpty();
    }

    @Override
    public String submitBookingCustomer(BookingData bookingData) {
        String name = bookingData.getName();
        String email = bookingData.getEmail();
        List<OrderLineDTO> orderLines = bookingData.getChosenRooms();
        LocalDate startDate = LocalDate.parse(bookingData.getStartDate());
        LocalDate endDate = LocalDate.parse(bookingData.getEndDate());

        SimpleCustomerDTO customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            customer = new SimpleCustomerDTO(name, email);
            customer = customerService.addCustomer(customer);
            System.out.println("New customer added: " + customer);
        }

        DetailedBookingDTO booking = new DetailedBookingDTO(customer, startDate, endDate);
        System.out.println("New booking: " + booking);

        booking = addBooking(booking);
        System.out.println("Added booking: " + booking);

        DetailedBookingDTO finalBooking = booking;
        orderLines.stream()
                .map(orderLine -> new DetailedOrderLineDTO(orderLine.getExtraBeds(), finalBooking, roomService.getRoomByID((long) orderLine.getId())))
                .forEach(orderLineService::addOrderLine);

        return "Everything is fine";
    }

    @Override
    public int getNumberOfRoomsFromBooking(Long id) {
        List<SimpleOrderLineDTO> orderLines = orderLineService.getOrderLinesByBookingId(id);
        return orderLines.size();
    }

    @Override
    public int getNumberOfBedsFromBooking(Long id) {
        List<SimpleOrderLineDTO> orderLines = orderLineService.getOrderLinesByBookingId(id);

        return orderLines.stream()
                .mapToInt(SimpleOrderLineDTO::getExtraBeds)
                .sum();
    }
}