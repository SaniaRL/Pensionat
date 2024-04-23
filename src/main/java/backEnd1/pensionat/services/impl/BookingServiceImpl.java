package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.services.interfaces.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;

    public BookingServiceImpl(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<DetailedBookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream().map(this::bookingToDetailedBookingDTO).toList();
    }

    @Override
    public DetailedBookingDTO bookingToDetailedBookingDTO(Booking booking) {
        return DetailedBookingDTO.builder().id(booking.getId())
                .customer(new SimpleCustomerDTO(booking.getCustomer().getId(),
                        booking.getCustomer().getName(), booking.getCustomer().getEmail()))
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).build();
    }

    @Override
    public SimpleBookingDTO bookingToSimpleBookingDTO(Booking booking) {
        return null;
    }


    @Override
    public String addBooking(Booking b) {
        bookingRepo.save(b);
        return "Booking added successfully";
    }

    @Override
    public String removeBookingById(Long id) {
        bookingRepo.deleteById(id);
        return "Booking removed successfully";
    }
}
