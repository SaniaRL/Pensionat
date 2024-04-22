package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.Models.Booking;

import java.util.List;

public interface BookingService {
//    public BookingDto bookingToBookingDto(Booking c);

//    public Booking bookingDtoToBooking(BookingDto booking);

    List<Booking> getAllBookings();
    String addBooking(Booking c);
    String removeBookingById(Long id);
}
