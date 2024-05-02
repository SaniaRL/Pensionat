package com.example.pensionat.services.impl;

import com.example.pensionat.models.OrderLine;
import com.example.pensionat.repositories.OrderLineRepo;
import com.example.pensionat.services.convert.OrderLineConverter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public
class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final RoomServicelmpl roomService;
    private final OrderLineServicelmpl orderLineService;
    private final OrderLineRepo orderLineRepo;

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo,
                              CustomerService customerService, RoomServicelmpl roomServicelmpl,
                              OrderLineServicelmpl orderLineService, OrderLineRepo orderLineRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.customerService = customerService;
        this.roomService = roomServicelmpl;
        this.orderLineService = orderLineService;
        this.orderLineRepo = orderLineRepo;
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
        return BookingConverter.bookingToDetailedBookingDTO(bookingRepo
                .save(BookingConverter.detailedBookingDTOtoBooking(b)));
    }

    @Override
    public DetailedBookingDTO updateBooking(DetailedBookingDTO b) {
        return BookingConverter.bookingToDetailedBookingDTO(bookingRepo
                .save(BookingConverter.detailedBookingDTOtoBooking(b)));
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
    public List<Integer> submitBookingCustomer(BookingData bookingData) {
        Long bookingId = bookingData.getId();
        String name = bookingData.getName();
        String email = bookingData.getEmail();
        List<OrderLineDTO> chosenRooms = bookingData.getChosenRooms();
        LocalDate startDate = LocalDate.parse(bookingData.getStartDate());
        LocalDate endDate = LocalDate.parse(bookingData.getEndDate());
        SimpleCustomerDTO customer = customerService.getCustomerByEmail(email);
        DetailedBookingDTO booking;

        if(bookingData.getId() == -1L){
            if (customer == null) {
                customer = new SimpleCustomerDTO(name, email);
                customer = customerService.addCustomer(customer);
                System.out.println("New customer added: " + customer);
            }
            booking = new DetailedBookingDTO(customer, startDate, endDate);
            booking = addBooking(booking);
            DetailedBookingDTO finalBooking = booking;
            chosenRooms.stream()
                    .map(orderLine -> new DetailedOrderLineDTO(orderLine.getExtraBeds(), finalBooking, roomService.getRoomByID((long) orderLine.getId())))
                    .forEach(orderLineService::addOrderLine);
        }
        else {
            List<OrderLineDTO> booked = getBookedRooms(startDate, endDate, chosenRooms, bookingId);
            if(!booked.isEmpty()){
                return new ArrayList<>(booked.stream().map(OrderLineDTO::getId).toList());
            }

            booking = new DetailedBookingDTO(bookingId, customer, startDate, endDate);

            List<DetailedOrderLineDTO> orderLines = orderLineService.getDetailedOrderLinesByBookingId(bookingId);

            List<Long> chosenRoomIds = chosenRooms.stream()
                    .map(r -> (long) r.getId()).toList();

            List<DetailedOrderLineDTO> updateRooms = orderLines.stream()
                    .filter(r -> chosenRoomIds.contains(r.getRoom().getId())).toList();

            updateRooms.forEach(ur -> {
                chosenRooms.forEach(cr -> {
                    if (cr.getId() == ur.getRoom().getId()) {
                        ur.setExtraBeds(cr.getExtraBeds());
                    }
                });
            });


            List<DetailedOrderLineDTO> deleteRooms = orderLines.stream()
                    .filter(r -> !(chosenRoomIds.contains(r.getRoom().getId()))).toList();

            List<OrderLineDTO> saveRooms = chosenRooms.stream()
                    .filter(r -> !(orderLines.stream()
                            .map(o -> o.getRoom().getId())
                            .toList())
                            .contains((long) r.getId()))
                    .toList();

            booking = addBooking(booking);
            DetailedBookingDTO finalBooking = booking;

            deleteRooms.forEach(dr -> orderLineRepo.deleteById(dr.getId()));

            updateRooms.forEach(ur -> {
                orderLineRepo.save(OrderLineConverter.detailedOrderLineDtoToOrderLine(ur, BookingConverter.detailedBookingDTOtoBooking(finalBooking)));
            });

            saveRooms.stream()
                    .map(orderLine -> new DetailedOrderLineDTO(orderLine.getExtraBeds(), finalBooking, roomService.getRoomByID((long) orderLine.getId())))
                    .forEach(orderLineService::addOrderLine);

        }
        return new ArrayList<>();
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

    public List<OrderLineDTO> getBookedRooms(LocalDate startDate, LocalDate endDate, List<OrderLineDTO> rooms, Long id) {
        List<Booking> bookings = bookingRepo.findByStartDateLessThanAndEndDateGreaterThanAndIdNot(endDate, startDate, id);
        List<OrderLine> orderLines = new ArrayList<>();
        bookings.forEach(b -> orderLines.addAll(orderLineRepo.findAllByBookingId(b.getId())));

        List<OrderLineDTO> booked = new ArrayList<>();

        orderLines.forEach(ol -> {
            rooms.forEach(cr -> {
                if (ol.getRoom().getId() == cr.getId()) {
                    booked.add(cr);
                }
            });
        });

        return booked;
    }
}