package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.services.convert.BookingConverter;
import backEnd1.pensionat.services.interfaces.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;

    public BookingServiceImpl(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<DetailedBookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(BookingConverter::bookingToDetailedBookingDTO)
                .toList();
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
