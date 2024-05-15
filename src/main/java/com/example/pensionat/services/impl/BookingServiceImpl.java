package com.example.pensionat.services.impl;

import com.example.pensionat.models.Customer;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.repositories.OrderLineRepo;
import com.example.pensionat.services.convert.OrderLineConverter;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.models.Booking;
import com.example.pensionat.repositories.BookingRepo;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.dtos.*;
import com.example.pensionat.services.convert.BookingConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final CustomerService customerService;
    private final RoomServicelmpl roomService;
    private final OrderLineServicelmpl orderLineService;
    private final OrderLineRepo orderLineRepo;

    @PersistenceContext
    EntityManager entityManager;


    public BookingServiceImpl(BookingRepo bookingRepo,
                              CustomerService customerService, RoomServicelmpl roomServicelmpl,
                              OrderLineServicelmpl orderLineService, OrderLineRepo orderLineRepo) {
        this.bookingRepo = bookingRepo;
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

    private List<OrderLineDTO> getBookedRooms(LocalDate startDate, LocalDate endDate, List<OrderLineDTO> rooms, Long id) {
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

    @Override
    public double generatePrice(BookingData bookingData){

        LocalDate startDate = LocalDate.parse(bookingData.getStartDate());
        LocalDate endDate = LocalDate.parse(bookingData.getEndDate());
        long numberOfNights = Math.abs(ChronoUnit.DAYS.between(startDate, endDate));

        //10 bokningar senaste året eller va?
        String customerEmail = bookingData.getEmail();
        boolean tenNightOrMore = tenOrMoreNights(customerEmail);


        //Kolla summan av alla rum för en standardnatt
        double sum = bookingData.getChosenRooms().stream()
                .mapToDouble(r -> roomService.getRoomByID((long) r.getId()).getPrice())
                .sum();


        System.out.println("sum for 1 night: " + sum);
        System.out.println("Start Date: " + bookingData.getStartDate());
        System.out.println("End Date: " + bookingData.getEndDate());
        System.out.println("number of numberOfNights: " + numberOfNights);
        System.out.println("Sum * numberOfNights: " + sum * numberOfNights);

        //Ev ändra baserat på hur vi tolkar saker
//        sum = sum * numberOfNights;

        double discount = 0;
        int nightsNeededForDiscount = 2;

        //- om man bokar två nätter eller fler får man automatiskt 0.5% rabatt
        if(numberOfNights >= nightsNeededForDiscount){
            discount += 0.005;
        }
        //Kolla om kunden har hyrt fler än 10 nätter det senaste året
        if(tenNightOrMore) {
            discount += 0.02;
        }

        System.out.println(discount);


        //- natten söndag till måndag ger alltid 2% rabatt
        int now = LocalDate.now().getDayOfWeek().getValue();

        List<LocalDate> days = startDate.datesUntil(endDate.plusDays(1)).toList();
        double newSum = days.stream().mapToDouble(day -> {
            if (days.indexOf(day) == 0) {
                return 0;
            }
            else if(day.getDayOfWeek() == DayOfWeek.MONDAY && days.indexOf(day) - 1 > -1) {
                System.out.println(day.getDayOfWeek().minus(1) + " -> " + day.getDayOfWeek() + " : " + sum * 0.98);
                return sum * 0.98;
            }
            else {
                System.out.println(day.getDayOfWeek().minus(1) + " -> " + day.getDayOfWeek() + " : " + sum);
                return sum;
            }
        }).sum();

        System.out.println(1-discount);

        return newSum * (1 - discount);
    }

    private boolean tenOrMoreNights(String email) {

        long customerId = customerService.getCustomerByEmail(email).getId();

        LocalDate now = LocalDate.now();
        LocalDate yearAgo = now.minusYears(1);

        //Get all bookings that fall within the 1-year timeframe -- where end date is after the 1-year mark.
        List<DetailedBookingDTO> bookings = bookingRepo.findByCustomerIdAndEndDateIsGreaterThanAndEndDateIsLessThan(customerId, yearAgo, now).stream().map(BookingConverter::bookingToDetailedBookingDTO).toList();

        //Get all the dates from the intervals excluding the check-in date as we only want to count overnight stays
        List<List<LocalDate>> dates = bookings.stream().map(b ->
                b.getStartDate().plusDays(1)
                        .datesUntil(b.getEndDate().plusDays(1)).toList())
                .toList();

        //Get the amount of those dates that fall within the 1-year frame from today.
        int staysWithinOneYear = dates.stream().mapToInt(dList -> dList.stream()
                .filter(d -> d.isAfter(yearAgo))
                .toList().size())
                .sum();

        return staysWithinOneYear >= 10;
    }
}