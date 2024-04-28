package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.BookingData;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleBookingDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;

import java.util.List;

public interface BookingService {
//    public BookingDto bookingToBookingDto(Booking c);
//    public Booking bookingDtoToBooking(BookingDto booking);

    List<DetailedBookingDTO> getAllBookings();
    DetailedBookingDTO addBooking(DetailedBookingDTO b);
    String removeBookingById(Long id);
    Booking bookingDtoToBooking(BookingDTO b, Customer c);
    Long addBookingFromBookingDto(BookingDTO b);
    DetailedBookingDTO getBookingById(Long id);
    DetailedBookingDTO bookingToDetailedBookingDto(Booking b);

    String submitBookingCustomer(BookingData bookingData);
}
