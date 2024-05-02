package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.BookingData;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.OrderLineDTO;


import java.util.List;

public interface BookingService {

    List<DetailedBookingDTO> getAllBookings();
    DetailedBookingDTO addBooking(DetailedBookingDTO b);


    String removeBookingById(Long id);
    Long addBookingFromBookingDto(BookingDTO b);
    DetailedBookingDTO getBookingById(Long id);
    boolean getBookingByCustomerId(Long customerId);
    String submitBookingCustomer(BookingData bookingData);

    int getNumberOfRoomsFromBooking(Long id);

    int getNumberOfBedsFromBooking(Long id);


    }